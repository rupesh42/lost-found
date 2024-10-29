package com.rupesh.assignment.lostfound.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This entity will save all the records of the user.
 * 
 * @author Rupesh
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(exclude = "claims")
public class LostandFoundUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String surname;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<ClaimRecord> claims;
}
