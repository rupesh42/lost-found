package com.rupesh.assignment.lostfound.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.rupesh.assignment.lostfound.domain.LostItem;
import com.rupesh.assignment.lostfound.domain.LostandFoundUser;
import com.rupesh.assignment.lostfound.repository.ClaimRecordRepository;
import com.rupesh.assignment.lostfound.repository.LostItemRepository;
import com.rupesh.assignment.lostfound.repository.LostandFoundUserRepository;
import com.rupesh.assignment.lostfound.utils.LostandFoundConstants;

class ClaimRecordServiceImplTest {

  @InjectMocks
  private ClaimRecordServiceImpl claimRecordService;

  @Mock
  private ClaimRecordRepository claimRecordRepository;

  @Mock
  private LostItemRepository lostItemRepository;

  @Mock
  private LostandFoundUserRepository lostItemUserRepository;

  private Long userId;
  private String itemId;
  private int quantity;
  private LostItem lostItem;
  private LostandFoundUser user;
  private LostandFoundUser user2;
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

    user2 = new LostandFoundUser();
    user2.setId(2L);
    user2.setName("Jane");
    user2.setSurname("Doe");

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
	void testItemAlreadyClaimedBySameUser() { 

		when(lostItemRepository.findById("ITEM001")).thenReturn(Optional.of(lostItem)); 
		when(lostItemUserRepository.findById(1L)).thenReturn(Optional.of(user)); 
		when(claimRecordRepository.existsByUserIdAndLostItem(1L, lostItem)).thenReturn(true); 

		String response = claimRecordService.claimLostItem(1L, "ITEM001", 1); 
		assertEquals("You have already claimed this item.", response); 

		verify(claimRecordRepository, never()).save(any(ClaimRecord.class));
		verify(lostItemRepository, never()).save(lostItem); 
	}

  @Test 
	void testItemAlreadyClaimedByAnotherUser() { 

		when(lostItemRepository.findById("ITEM001")).thenReturn(Optional.of(lostItem)); 
		when(lostItemUserRepository.findById(2L)).thenReturn(Optional.of(user2)); 

		when(claimRecordRepository.existsByUserIdAndLostItem(2L, lostItem)).thenReturn(false); 
		when(claimRecordRepository.existsByLostItemAndUserIdNot(lostItem, 2L)).thenReturn(true); 

		String response = claimRecordService.claimLostItem(2L, "ITEM001", 1); 
		assertEquals(LostandFoundConstants.ITEM_ALREADY_CLAIMED + LostandFoundConstants.CLAIMED_SUCCESS + user2.getName() + ".", response); 

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
