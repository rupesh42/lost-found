package com.rupesh.assignment.lostfound.service;

import java.util.List;

import com.rupesh.assignment.lostfound.domain.ClaimRecordDTO;

/**
 * Service for Claim Record service doing operations like claiming lost items and fetching all the claimed items.
 * @author Rupesh
 *
 */
public interface ClaimRecordService {

	public String claimLostItem(Long userId, String itemId, int quantity);

	public List<ClaimRecordDTO> getAllClaimedItems();
}
