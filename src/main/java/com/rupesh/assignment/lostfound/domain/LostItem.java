package com.rupesh.assignment.lostfound.domain;

import java.time.LocalDate;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rupesh.assignment.lostfound.utils.LostItemIDGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This entity is used for LostItems of which the data will be uploaded by user in CSV format.
 * Also adds isClaimed bydefault as false and is changed once a user claims any item.
 * @author Rupesh
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LostItem {

	@Id
    @GeneratedValue(generator = "custom-id-generator")
    @GenericGenerator(name = "custom-id-generator", type = LostItemIDGenerator.class)
    private String id;

    private String name;
    private LocalDate dateFound;
    private String locationFound;
    private int quantity;

    @OneToMany(mappedBy = "lostItem", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ClaimRecord> claimedBy;
    
    /**
     * by defalut false will be set, will change once the user claims the item.
     */
    private boolean isClaimed;

}
