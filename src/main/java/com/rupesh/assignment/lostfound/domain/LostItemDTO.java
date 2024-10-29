package com.rupesh.assignment.lostfound.domain;

import java.time.LocalDate;
import java.util.Set;

import lombok.Data;

@Data
public class LostItemDTO {

  private String id;

  private String name;
  private LocalDate dateFound;
  private String locationFound;
  private int quantity;

  private Set<ClaimRecord> claimedBy;

  private boolean isClaimed;

}
