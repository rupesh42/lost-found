package com.rupesh.assignment.lostfound.exception;

/**
 * This class will be used to throw custom made exception in cases of error
 * 
 * @author Rupesh
 *
 */
public class LostFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public LostFoundException(String message) {
    super(message);
  }

}
