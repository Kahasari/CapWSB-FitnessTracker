package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

class UserControllerTest {
    private UserServiceImpl userServiceImpl;
    private UserMapper userMapper;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userServiceImpl = Mockito.mock(UserServiceImpl.class);
        userMapper = Mockito.mock(UserMapper.class);
        userController = new UserController(userServiceImpl, userMapper);
    }

    @Test
    void getAllUsers() {
        User user1 = new User("Marek", "Konrad", LocalDate.of(1990, 1, 1), "czlowiekbeznazwiska@gmail.com");
        User user2 = new User("Olga", "Tokarczuk", LocalDate.of(1992, 2, 2), "olunia@gmail.com");
        List<User> users = List.of(user1, user2);
        when(userServiceImpl.findAllUsers()).thenReturn(users);

        List<UserDto> result = userController.getAllUsers();
        assertEquals(users.size(), result.size());
        for (int i = 0; i < users.size(); i++) {
            UserDto expectedUserDto = userMapper.toDto(users.get(i));
            assertEquals(expectedUserDto, result.get(i));
        }
    }
    @Test
    void addUser() {
        User user1 = new User("Olga", "Tokarczuk", LocalDate.of(1992, 2, 2), "olunia@gmail.com");
        when(userServiceImpl.createUser(user1)).thenReturn(user1);
        User newUser = userController.addUser(user1);
        assertEquals(user1, newUser);
    }
}