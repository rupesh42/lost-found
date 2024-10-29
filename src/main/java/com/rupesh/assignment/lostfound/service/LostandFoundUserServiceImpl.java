package com.rupesh.assignment.lostfound.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import com.rupesh.assignment.lostfound.domain.LostandFoundUser;
import com.rupesh.assignment.lostfound.domain.LostandFoundUserDTO;
import com.rupesh.assignment.lostfound.repository.LostandFoundUserRepository;

/**
 * This implementation saves the mock users automatically. It will also send the details of all
 * users if asked for.
 * 
 * @author Rupesh
 *
 */
@Service
public class LostandFoundUserServiceImpl implements CommandLineRunner, LostandFoundUserService {

  private final LostandFoundUserRepository userRepository;

  @Autowired
  public LostandFoundUserServiceImpl(LostandFoundUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<LostandFoundUserDTO> getAllUsers() {
    return userRepository.findAll().stream().map(this::convertToDto).toList();
  }

  public LostandFoundUserDTO saveUser(String name, String surname) {
    LostandFoundUser user = new LostandFoundUser();
    user.setName(name);
    user.setSurname(surname);
    LostandFoundUser savedUser = userRepository.save(user);
    return convertToDto(savedUser);
  }

  @Override
  public void run(String... args) throws Exception {
    saveUser("Jon", "Snow");
    saveUser("Sansa", "Stark");
    saveUser("Daney", "Targy");
  }

  private LostandFoundUserDTO convertToDto(LostandFoundUser user) {
    LostandFoundUserDTO userDTO = new LostandFoundUserDTO();
    userDTO.setId(user.getId());
    userDTO.setName(user.getName());
    userDTO.setSurname(user.getSurname());
    return userDTO;
  }
}
