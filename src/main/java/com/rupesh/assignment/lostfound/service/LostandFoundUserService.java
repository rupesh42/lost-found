package com.rupesh.assignment.lostfound.service;

import java.util.List;

import com.rupesh.assignment.lostfound.domain.LostandFoundUserDTO;

/**
 * This service handles user's operations like get all the users and save the details of users.
 * @author Rupesh
 *
 */
public interface LostandFoundUserService {

	public List<LostandFoundUserDTO> getAllUsers();

	public LostandFoundUserDTO saveUser(String name, String surname);

}
