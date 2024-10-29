package com.rupesh.assignment.lostfound.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rupesh.assignment.lostfound.domain.ClaimRecord;
import com.rupesh.assignment.lostfound.domain.ClaimRecordDTO;
import com.rupesh.assignment.lostfound.domain.LostandFoundUser;
import com.rupesh.assignment.lostfound.domain.LostItem;
import com.rupesh.assignment.lostfound.repository.ClaimRecordRepository;
import com.rupesh.assignment.lostfound.repository.LnFUserRepository;
import com.rupesh.assignment.lostfound.repository.LostItemRepository;

public class ClaimRecordServiceImplTest {

	@InjectMocks
	private ClaimRecordServiceImpl claimRecordService;

	@Mock
	private ClaimRecordRepository claimRecordRepository;

	@Mock
	private LostItemRepository lostItemRepository;

	@Mock
	private LnFUserRepository lostItemUserRepository;

	private Long userId;
	private String itemId;
	private int quantity;
	private LostItem lostItem;
	private LostandFoundUser user;
	private ClaimRecord claimRecord1;
	private ClaimRecord claimRecord2;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		initializeTestData();
	}

	private void initializeTestData() {
		userId = 1L;
		itemId = "ABC123";
		quantity = 1;

		lostItem = new LostItem();
		lostItem.setId(itemId);
		lostItem.setName("Lost Watch");
		lostItem.setQuantity(5);
		lostItem.setLocationFound("Park");
		lostItem.setDateFound(LocalDate.of(2024, 10, 1));
		lostItem.setClaimed(false);

		user = new LostandFoundUser();
		user.setId(userId);
		user.setName("John");
		user.setSurname("Doe");

		claimRecord1 = new ClaimRecord();
		claimRecord1.setId(1L);
		claimRecord1.setLostItem(lostItem);
		claimRecord1.setUser(user);
		claimRecord1.setQuantity(1);
		claimRecord1.setClaimDate(LocalDate.of(2024, 10, 2));

		claimRecord2 = new ClaimRecord();
		claimRecord2 = new ClaimRecord();
		claimRecord2.setId(2L);
		claimRecord2.setLostItem(lostItem);
		claimRecord2.setUser(user);
		claimRecord2.setQuantity(2);
		claimRecord2.setClaimDate(LocalDate.of(2024, 10, 3));

		when(lostItemRepository.findById(itemId)).thenReturn(Optional.of(lostItem));
		when(lostItemUserRepository.findById(userId)).thenReturn(Optional.of(user));
		when(claimRecordRepository.findAll()).thenReturn(Arrays.asList(claimRecord1, claimRecord2));
	}

	@Test
    void testClaimLostItem() {
        when(claimRecordRepository.findByLostItem(lostItem)).thenReturn(List.of());

        String response = claimRecordService.claimLostItem(userId, itemId, quantity);

        assertEquals("Item Lost Watch successfully claimed by John.", response);
        assertEquals(5, lostItem.getQuantity());
        assertFalse(lostItem.isClaimed());
        verify(claimRecordRepository).save(any(ClaimRecord.class));
        verify(lostItemRepository).save(lostItem);
    }

	@Test
	void testClaimLostItem_NotEnoughQuantity() {
		quantity = 10;

		String response = claimRecordService.claimLostItem(userId, itemId, quantity);

		assertEquals("Not enough quantity available to claim.", response);
		assertEquals(5, lostItem.getQuantity());
		verify(claimRecordRepository, never()).save(any(ClaimRecord.class));
		verify(lostItemRepository, never()).save(lostItem);
	}

	@Test
	void testClaimLostItem_AlreadyClaimed() {
		ClaimRecord existingClaim = new ClaimRecord();
		existingClaim.setLostItem(lostItem);
		existingClaim.setUser(user);

		when(claimRecordRepository.findByLostItem(lostItem)).thenReturn(List.of(existingClaim));

		assertEquals(" This item has already been claimed by another user. However, Also successfully claimed by John.",
				claimRecordService.claimLostItem(userId, itemId, quantity));
		assertEquals(5, lostItem.getQuantity());
		verify(claimRecordRepository).save(any(ClaimRecord.class));
		verify(lostItemRepository).save(lostItem);
	}

	@Test
	void testGetAllClaimedItems() {

		List<ClaimRecordDTO> claimedItems = claimRecordService.getAllClaimedItems();
		assertEquals(2, claimedItems.size());

		ClaimRecordDTO dto1 = claimedItems.get(0);
		assertEquals(claimRecord1.getId(), dto1.getId());
		assertEquals(claimRecord1.getLostItem().getId(), dto1.getLostItem().getId());
		assertEquals(claimRecord1.getUser().getId(), dto1.getUser().getId());
		assertEquals(claimRecord1.getQuantity(), dto1.getQuantity());
		assertEquals(claimRecord1.getClaimDate(), dto1.getClaimDate());

		ClaimRecordDTO dto2 = claimedItems.get(1);
		assertEquals(claimRecord2.getId(), dto2.getId());
		assertEquals(claimRecord2.getLostItem().getId(), dto2.getLostItem().getId());
		assertEquals(claimRecord2.getUser().getId(), dto2.getUser().getId());
		assertEquals(claimRecord2.getQuantity(), dto2.getQuantity());
		assertEquals(claimRecord2.getClaimDate(), dto2.getClaimDate());
		verify(claimRecordRepository).findAll();
	}

}
