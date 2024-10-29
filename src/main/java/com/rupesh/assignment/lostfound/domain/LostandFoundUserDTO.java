package com.rupesh.assignment.lostfound.domain;

import java.util.Set;

import lombok.Data;

/**
 * DTO handling the operations for Users
 * 
 * @author Rupesh
 *
 */
@Data
public class LostandFoundUserDTO {

  private Long id;

  private String name;

  private String surname;

  private Set<ClaimRecord> claims;

}
