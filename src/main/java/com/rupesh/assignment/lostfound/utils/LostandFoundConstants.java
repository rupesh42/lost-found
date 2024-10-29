package com.rupesh.assignment.lostfound.utils;

/**
 * All the constant Strings, error, warning message to the users are store here.
 * 
 * @author Rupesh
 *
 */
public class LostandFoundConstants {

  private LostandFoundConstants() {}

  public static final String ITEM_NOT_FOUND = "Lost item not found";

  public static final String USER_NOT_FOUND = "User not found";

  public static final String QUANTITY_LESS_THAN_REQUESTED =
      "Not enough quantity available to claim.";

  public static final String ITEM_ALREADY_CLAIMED =
      " This item has already been claimed by another user. However, Also";

  public static final String CLAIMED_SUCCESS = " successfully claimed by ";

  public static final String DATE_FORMAT_DMY = "dd-MM-yyyy";

  public static final String IMPORTED_SUCCESS = "Lost Items are succesfully imported in database";

  public static final String QUANTITY_IS_ZERO = "Quantity cannot be zero";

  public static final String NO_CLAIMS = "There are no claims currently";
}
