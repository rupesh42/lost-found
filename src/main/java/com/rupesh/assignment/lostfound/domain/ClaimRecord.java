package com.rupesh.assignment.lostfound.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This Entity will have all the claim records, which will be processed and updated once a user
 * claims a product
 * 
 * @author Rupesh
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClaimRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private LostandFoundUser user;

  @ManyToOne
  @JoinColumn(name = "lost_item_id")
  private LostItem lostItem;

  private int quantity;

  /**
   * always used as today's date, the date the claim happened.
   */
  private LocalDate claimDate;

  public String getUserName() {
    return user.getName() + " " + user.getSurname();
  }

  public String getProductName() {
    return lostItem.getName();
  }
}
