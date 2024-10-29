package com.rupesh.assignment.lostfound.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rupesh.assignment.lostfound.domain.ClaimRecord;
import com.rupesh.assignment.lostfound.domain.ClaimRecordDTO;
import com.rupesh.assignment.lostfound.domain.LostItem;
import com.rupesh.assignment.lostfound.domain.LostItemDTO;
import com.rupesh.assignment.lostfound.domain.LostandFoundUser;
import com.rupesh.assignment.lostfound.domain.LostandFoundUserDTO;
import com.rupesh.assignment.lostfound.exception.LostFoundException;
import com.rupesh.assignment.lostfound.repository.ClaimRecordRepository;
import com.rupesh.assignment.lostfound.repository.LostItemRepository;
import com.rupesh.assignment.lostfound.repository.LostandFoundUserRepository;
import com.rupesh.assignment.lostfound.utils.LostandFoundConstants;

/**
 * this implementation send requests to repositories and brings back the values.
 * 
 * @author Rupesh
 *
 */
@Service
public class ClaimRecordServiceImpl implements ClaimRecordService {

  private final ClaimRecordRepository claimRecordRepository;

  private final LostItemRepository lostItemRepository;

  private final LostandFoundUserRepository lostItemUserRepository;

  @Autowired
  public ClaimRecordServiceImpl(ClaimRecordRepository claimRecordRepository,
      LostItemRepository lostItemRepository, LostandFoundUserRepository lostItemUserRepository) {
    this.claimRecordRepository = claimRecordRepository;
    this.lostItemRepository = lostItemRepository;
    this.lostItemUserRepository = lostItemUserRepository;
  }

  /**
   * Once the claims the lost item, the quantity will be validated; the customer ID and the item ID
   * will also be validated and will give an exception to user if the user or item is not found. In
   * case the quantity claimed by user is more than the found items, user will an exception. Once
   * all the validations are done, a insert record will be sent to the claim record entity;
   * parallely, it will also set the isClaied to True in Lost Item entity.
   * 
   * In case the item is already claimed by the same customer, the user will get an error, and if
   * another customer is trying to claim, the user will get a warning but the details will be
   * stored.
   */

  @Override
  public String claimLostItem(Long userId, String itemId, int quantity) {
    if (quantity < 1) {
      throw new IllegalArgumentException(LostandFoundConstants.QUANTITY_IS_ZERO);
    }

    LostItem lostItem = lostItemRepository.findById(itemId)
        .orElseThrow(() -> new LostFoundException(LostandFoundConstants.ITEM_NOT_FOUND));
    LostandFoundUser user = lostItemUserRepository.findById(userId)
        .orElseThrow(() -> new LostFoundException(LostandFoundConstants.USER_NOT_FOUND));

    // Check if the same user has already claimed this item
    boolean alreadyClaimedByUser =
        claimRecordRepository.existsByUserIdAndLostItem(userId, lostItem);

    if (alreadyClaimedByUser) {
      return "You have already claimed this item.";
    }

    ClaimRecord claimRecord = new ClaimRecord();
    claimRecord.setLostItem(lostItem);
    claimRecord.setUser(user);
    claimRecord.setQuantity(quantity);
    claimRecord.setClaimDate(LocalDate.now());

    claimRecordRepository.save(claimRecord);

    // update Lost Item database that the item is claimed
    lostItem.setClaimed(true);
    lostItemRepository.save(lostItem);

    // Check if the item was already claimed by another user
    boolean alreadyClaimedByAnotherUser =
        claimRecordRepository.existsByLostItemAndUserIdNot(lostItem, userId);

    if (alreadyClaimedByAnotherUser) {
      return LostandFoundConstants.ITEM_ALREADY_CLAIMED + LostandFoundConstants.CLAIMED_SUCCESS
          + user.getName() + ".";
    }

    return "Item " + lostItem.getName() + LostandFoundConstants.CLAIMED_SUCCESS + user.getName()
        + ".";
  }



  /**
   * It fetched all the claimed Items list and sends it back to the user.
   */
  @Override
  public List<ClaimRecordDTO> getAllClaimedItems() {
    return claimRecordRepository.findAll().stream().map(this::convertToDto).toList();
  }

  /**
   * The convertors heps to map the DTO with entities.
   * 
   * @param claimRecord
   * @return
   */
  private ClaimRecordDTO convertToDto(ClaimRecord claimRecord) {
    ClaimRecordDTO claimRecordDTO = new ClaimRecordDTO();
    claimRecordDTO.setId(claimRecord.getId());
    claimRecordDTO.setLostItem(convertToLostItemDto(claimRecord.getLostItem()));
    claimRecordDTO.setUser(convertToUserDto(claimRecord.getUser()));
    claimRecordDTO.setQuantity(claimRecord.getQuantity());
    claimRecordDTO.setClaimDate(claimRecord.getClaimDate());
    return claimRecordDTO;
  }

  private LostItemDTO convertToLostItemDto(LostItem lostItem) {
    LostItemDTO lostItemDTO = new LostItemDTO();
    lostItemDTO.setId(lostItem.getId());
    lostItemDTO.setName(lostItem.getName());
    lostItemDTO.setQuantity(lostItem.getQuantity());
    lostItemDTO.setLocationFound(lostItem.getLocationFound());
    lostItemDTO.setDateFound(lostItem.getDateFound());
    lostItemDTO.setClaimed(lostItem.isClaimed());
    return lostItemDTO;
  }

  private LostandFoundUserDTO convertToUserDto(LostandFoundUser lostItemUser) {
    LostandFoundUserDTO lostItemUserDTO = new LostandFoundUserDTO();
    lostItemUserDTO.setId(lostItemUser.getId());
    lostItemUserDTO.setName(lostItemUser.getName());
    lostItemUserDTO.setSurname(lostItemUser.getSurname());
    return lostItemUserDTO;
  }
}
