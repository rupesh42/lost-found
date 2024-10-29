package com.rupesh.assignment.lostfound.domain;

import java.time.LocalDate;

import lombok.Data;

/**
 * A DTO to perform operations on Claim Record.
 * 
 * @author Rupesh
 *
 */
@Data
public class ClaimRecordDTO {

  private Long id;

  private LostandFoundUserDTO user;

  private LostItemDTO lostItem;

  private int quantity;

  private LocalDate claimDate;
}
