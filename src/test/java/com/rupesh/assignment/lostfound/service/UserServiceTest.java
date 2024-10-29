package com.rupesh.assignment.lostfound.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rupesh.assignment.lostfound.domain.LostandFoundUser;
import com.rupesh.assignment.lostfound.domain.LostandFoundUserDTO;
import com.rupesh.assignment.lostfound.repository.LostandFoundUserRepository;

public class UserServiceTest {

  @InjectMocks
  private LostandFoundUserServiceImpl userService;

  @Mock
  private LostandFoundUserRepository userRepository;

  private LostandFoundUser user1;
  private LostandFoundUser user2;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    initializeTestData();
  }

  private void initializeTestData() {
    user1 = new LostandFoundUser();
    user1.setId(1L);
    user1.setName("John");
    user1.setSurname("Doe");

    user2 = new LostandFoundUser();
    user2.setId(2L);
    user2.setName("Jane");
    user2.setSurname("Doe");

    when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
  }

  @Test
  void testGetAllUsers() {
    List<LostandFoundUserDTO> users = userService.getAllUsers();

    assertEquals(2, users.size());

    LostandFoundUserDTO dto1 = users.get(0);
    assertEquals(user1.getId(), dto1.getId());
    assertEquals(user1.getName(), dto1.getName());
    assertEquals(user1.getSurname(), dto1.getSurname());

    LostandFoundUserDTO dto2 = users.get(1);
    assertEquals(user2.getId(), dto2.getId());
    assertEquals(user2.getName(), dto2.getName());
    assertEquals(user2.getSurname(), dto2.getSurname());

    verify(userRepository).findAll();
  }

  @Test
  void testSaveUser() {
    String name = "Alice";
    String surname = "Smith";

    LostandFoundUser savedUser = new LostandFoundUser();
    savedUser.setId(3L);
    savedUser.setName(name);
    savedUser.setSurname(surname);

    when(userRepository.save(any(LostandFoundUser.class))).thenReturn(savedUser);

    LostandFoundUserDTO result = userService.saveUser(name, surname);

    assertNotNull(result);
    assertEquals(savedUser.getId(), result.getId());
    assertEquals(savedUser.getName(), result.getName());
    assertEquals(savedUser.getSurname(), result.getSurname());

    verify(userRepository).save(any(LostandFoundUser.class));
  }

}
