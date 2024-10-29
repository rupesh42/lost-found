package com.rupesh.assignment.lostfound.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rupesh.assignment.lostfound.domain.LostItem;
import com.rupesh.assignment.lostfound.domain.LostItemDTO;
import com.rupesh.assignment.lostfound.repository.LostItemRepository;
import com.rupesh.assignment.lostfound.utils.LostandFoundConstants;

/**
 * This implementation will do all the operations related to the Lost Items.
 * 
 * @author Rupesh
 *
 */
@Service
public class LostItemServiceImpl implements LostItemService {

  private final LostItemRepository lostItemRepository;

  private static final Logger LOG = LoggerFactory.getLogger(LostItemServiceImpl.class);

  @Autowired
  public LostItemServiceImpl(LostItemRepository lostItemRepository) {
    this.lostItemRepository = lostItemRepository;
  }

  @Override
  public List<LostItemDTO> getAllLostItems() {
    return lostItemRepository.findAll().stream().map(this::convertToDto).toList();
  }

  /**
   * This service will process each record from the CSV and store it in LostItem entity.
   */
  @Override
  public void processCSVFile(MultipartFile file) throws IOException {

    try (
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setHeader()
            .setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true).build())) {

      List<LostItem> lostItems = csvParser.getRecords().stream().map(csvRecord -> {
        LostItem lostItem = new LostItem();
        lostItem.setName(csvRecord.get(0)); // Column 0: Item Name
        lostItem.setQuantity(Integer.parseInt(csvRecord.get(1))); // Column 1: Quantity
        lostItem.setLocationFound(csvRecord.get(2)); // Column 2: Place
        lostItem.setDateFound(LocalDate.parse(csvRecord.get(3),
            DateTimeFormatter.ofPattern(LostandFoundConstants.DATE_FORMAT_DMY))); // Column 3: Date
                                                                                  // Found
        return lostItem;
      }).collect(Collectors.toList());

      lostItemRepository.saveAll(lostItems);
      LOG.info(LostandFoundConstants.IMPORTED_SUCCESS);
    }
  }

  /**
   * This convertors helps to convert the Entity to DTO
   * 
   * @param lostItem
   * @return
   */
  private LostItemDTO convertToDto(LostItem lostItem) {
    LostItemDTO lostItemDTO = new LostItemDTO();
    lostItemDTO.setId(lostItem.getId());
    lostItemDTO.setName(lostItem.getName());
    lostItemDTO.setQuantity(lostItem.getQuantity());
    lostItemDTO.setLocationFound(lostItem.getLocationFound());
    lostItemDTO.setDateFound(lostItem.getDateFound());
    lostItemDTO.setClaimed(lostItem.isClaimed());
    return lostItemDTO;
  }

}
