package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void createUser() {
        User user = new User("Adam", "Małysz", LocalDate.of(1977, 12, 1), "adam.malysz@gmail.com");
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);
        Assertions.assertEquals(user, createdUser);
    }

    @Test
    void getUser() {
        User user = new User("Adam", "Nawałka", LocalDate.of(1950, 2, 3), "nawalka@adamek.com");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUser(1L);
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(user, foundUser.get());
    }

    @Test
    void getUserByEmail() {
        User user = new User("Włodek", "Zelensky", LocalDate.of(1978, 1, 25), "sendWeapon@gmail.com");
        when(userRepository.findByEmail("sendWeapon@gmail.com")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserByEmail("sendWeapon@gmail.com");
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(user, foundUser.get());
    }

    @Test
    void findAllUsers() {
        User user1 = new User("Adam", "Małysz", LocalDate.of(1977, 12, 1), "adam.malysz@gmail.com");
        User user2 = new User("Adam", "Nawałka", LocalDate.of(1950, 2, 3), "nawalka@adamek.com");
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userService.findAllUsers();
        Assertions.assertEquals(users, allUsers);
    }

    @Test
    void getAllUserSummaries() {
        User user1 = new User("Adam", "Małysz", LocalDate.of(1977, 12, 1), "adam.malysz@gmail.com");
        user1.setId(1L);
        User user2 = new User("Adam", "Nawałka", LocalDate.of(1950, 2, 3), "nawalka@adamek.com");
        user2.setId(2L);
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<UserSummaryDTO> summaries = userService.getAllUserSummaries();
        Assertions.assertEquals(2, summaries.size());
        Assertions.assertEquals("Adam Małysz", summaries.get(0).name());
        Assertions.assertEquals("Adam Nawałka", summaries.get(1).name());
    }

    @Test
    public void deleteUser() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    private <T> CrudRepository verify(UserRepository userRepository, Times times) {
        return null;
    }

    @Test
    void updateUser() {
        User user = new User("Włodek", "Zelensky", LocalDate.of(1978, 1, 25), "sendWeapon@gmail.com");
        user.setId(1L);
        User updatedUser = new User("Nikita", "Chruszczow", LocalDate.of(1955, 6, 3), "chruszcz@gmail.com.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(1L, updatedUser);
        Assertions.assertEquals(updatedUser, result);
    }

    @Test
    void searchByEmail() {
        User user = new User("Nikita", "Chruszczow", LocalDate.of(1955, 6, 3), "chruszcz@gmail.com.com");
        List<User> users = List.of(user);
        when(userRepository.findByEmailContainingIgnoreCase("nikita")).thenReturn(users);

        List<User> foundUsers = userService.searchByEmail("nikita");
        Assertions.assertEquals(users, foundUsers);
    }
}