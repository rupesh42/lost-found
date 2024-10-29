package com.rupesh.assignment.lostfound.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.rupesh.assignment.lostfound.domain.LostItem;
import com.rupesh.assignment.lostfound.domain.LostItemDTO;
import com.rupesh.assignment.lostfound.repository.LostItemRepository;

public class LostItemServiceTest {

  @InjectMocks
  private LostItemServiceImpl lostItemService;

  @Mock
  private LostItemRepository lostItemRepository;

  @Mock
  private MultipartFile file;

  private LostItem lostItem1;
  private LostItem lostItem2;
  private List<LostItem> lostItems;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    initializeTestData();
  }

  private void initializeTestData() {
    lostItem1 = new LostItem();
    lostItem1.setId("ITEM001");
    lostItem1.setName("Lost Watch");
    lostItem1.setQuantity(5);
    lostItem1.setLocationFound("Park");
    lostItem1.setDateFound(LocalDate.of(2024, 10, 1));
    lostItem1.setClaimed(false);

    lostItem2 = new LostItem();
    lostItem2.setId("ITEM002");
    lostItem2.setName("Lost Phone");
    lostItem2.setQuantity(3);
    lostItem2.setLocationFound("Mall");
    lostItem2.setDateFound(LocalDate.of(2024, 10, 5));
    lostItem2.setClaimed(false);

    lostItems = Arrays.asList(lostItem1, lostItem2);

    when(lostItemRepository.findAll()).thenReturn(lostItems);
    when(lostItemRepository.save(any(LostItem.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
  }

  @Test
  void testGetAllLostItems() {
    List<LostItemDTO> lostItemDTOs = lostItemService.getAllLostItems();

    assertEquals(2, lostItemDTOs.size());

    LostItemDTO dto1 = lostItemDTOs.get(0);
    assertEquals(lostItem1.getId(), dto1.getId());
    assertEquals(lostItem1.getName(), dto1.getName());
    assertEquals(lostItem1.getQuantity(), dto1.getQuantity());
    assertEquals(lostItem1.getLocationFound(), dto1.getLocationFound());
    assertEquals(lostItem1.getDateFound(), dto1.getDateFound());
    assertEquals(lostItem1.isClaimed(), dto1.isClaimed());

    LostItemDTO dto2 = lostItemDTOs.get(1);
    assertEquals(lostItem2.getId(), dto2.getId());
    assertEquals(lostItem2.getName(), dto2.getName());
    assertEquals(lostItem2.getQuantity(), dto2.getQuantity());
    assertEquals(lostItem2.getLocationFound(), dto2.getLocationFound());
    assertEquals(lostItem2.getDateFound(), dto2.getDateFound());
    assertEquals(lostItem2.isClaimed(), dto2.isClaimed());

    verify(lostItemRepository).findAll();
  }

  @Test
  void testProcessCSVFile() throws IOException {
    String csvContent =
        "name,quantity,location,dateFound\nLost Watch,5,Park,01-10-2024\nLost Phone,3,Mall,05-10-2024\n";
    when(file.getInputStream())
        .thenReturn(new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8)));
    lostItemService.processCSVFile(file);
    verify(lostItemRepository, times(1)).saveAll(anyList());
  }

}
