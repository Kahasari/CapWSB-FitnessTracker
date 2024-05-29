//package com.capgemini.wsb.fitnesstracker.user.internal;
//
//import com.capgemini.wsb.fitnesstracker.user.api.User;
//import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class UserControllerTest {
//    private UserServiceImpl userServiceImpl;
//    private UserMapper userMapper;
//    private UserController userController;
//
//    @BeforeEach
//    void setUp() {
//        userServiceImpl = Mockito.mock(UserServiceImpl.class);
//        userMapper = Mockito.mock(UserMapper.class);
//        userController = new UserController(userServiceImpl, userMapper);
//    }
//
//    /**
//     Testy dla metod
//     */
//    @Test
//    void getAllUsers() {
//        User user1 = new User("Marek", "Konrad", LocalDate.of(1990, 1, 1), "czlowiekbeznazwiska@gmail.com");
//        User user2 = new User("Olga", "Tokarczuk", LocalDate.of(1992, 2, 2), "olunia@gmail.com");
//        List<User> users = List.of(user1, user2);
//        when(userServiceImpl.findAllUsers()).thenReturn(users);
//
//        List<UserDto> result = userController.getAllUsers();
//        assertEquals(users.size(), result.size());
//        for (int i = 0; i < users.size(); i++) {
//            UserDto expectedUserDto = userMapper.toDto(users.get(i));
//            assertEquals(expectedUserDto, result.get(i));
//        }
//    }
//    @Test
//    void addUser() {
//        User user1 = new User("Olga", "Tokarczuk", LocalDate.of(1992, 2, 2), "olunia@gmail.com");
//        when(userServiceImpl.createUser(user1)).thenReturn(user1);
//        User newUser = userController.addUser(user1);
//        assertEquals(user1, newUser);
//    }
//    @Test
//    void findUserById() {
//        User user = new User("Olga", "Tokarczuk", LocalDate.of(1992, 2, 2), "olunia@gmail.com");
//        user.setId(1L);
//        UserDto userDto = new UserDto(user.getId(), user.getFirstName(), user.getLastName(),user.getBirthdate(), user.getEmail());
//        when(userServiceImpl.getUser(1L)).thenReturn(Optional.of(user));
//        when(userMapper.toDto(user)).thenReturn(userDto);
//
//        UserDto result = userController.findUserById(1L);
//        assertEquals(userDto, result);
//    }
//
//    @Test
//    void findUserById_UserNotFound() {
//        when(userServiceImpl.getUser(1L)).thenReturn(Optional.empty());
//        assertThrows(UserNotFoundException.class, () -> userController.findUserById(1L));
//    }
//
//    @Test
//    void deleteUser() {
//        userController.removeUser(1L);
//        verify(userServiceImpl, times(1)).deleteUser(1L);
//    }
//
//    @Test
//    void upToDateUser() {
//        User user = new User("Olga", "Tokarczuk", LocalDate.of(1992, 2, 2), "olunia@gmail.com");
//        user.setId(1L);
//        UserDto userDto = new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getBirthdate(), user.getEmail());
//        when(userServiceImpl.updateUser(1L, user)).thenReturn(user);
//        when(userMapper.toDto(user)).thenReturn(userDto);
//
//        UserDto result = userController.upToDateUser(1L, user);
//        assertEquals(userDto, result);
//    }
//
//    @Test
//    void findUserByEmail() {
//        User user = new User("Olga", "Tokarczuk", LocalDate.of(1992, 2, 2), "olunia@gmail.com");
//        when(userServiceImpl.getUserByEmail("olunia@gmail.com")).thenReturn(Optional.of(user));
//
//        Optional<User> result = userController.findUserByEmail("olunia@gmail.com");
//        assertEquals(Optional.of(user), result);
//    }
//
//    @Test
//    void searchForUsersOlderThanX() {
//        User user1 = new User("Marek", "Konrad", LocalDate.of(1990, 1, 1), "czlowiekbeznazwiska@gmail.com");
//        User user2 = new User("Olga", "Tokarczuk", LocalDate.of(1992, 2, 2), "olunia@gmail.com");
//        List<User> users = List.of(user1, user2);
//        when(userServiceImpl.findUsersOlderThanX(30)).thenReturn(users);
//        when(userMapper.toDto(user1)).thenReturn(new UserDto(user1.getId(), user1.getFirstName(), user1.getLastName(), user1.getBirthdate(),user1.getEmail()));
//        when(userMapper.toDto(user2)).thenReturn(new UserDto(user2.getId(), user2.getFirstName(), user2.getLastName(), user2.getBirthdate(),user2.getEmail()));
//
//        List<UserDto> result = userController.searchForUsersOlderThanX(30);
//        assertEquals(users.size(), result.size());
//    }
//
//    @Test
//    void findUsersByEmailContainingIgnoreCase() {
//        User user1 = new User("Marek", "Konrad", LocalDate.of(1990, 1, 1), "czlowiekbeznazwiska@gmail.com");
//        User user2 = new User("Olga", "Tokarczuk", LocalDate.of(1992, 2, 2), "olunia@gmail.com");
//        List<User> users = List.of(user1, user2);
//        when(userServiceImpl.findUsersByEmailContainingIgnoreCase("gmail")).thenReturn(users);
//        when(userMapper.toDto(user1)).thenReturn(new UserDto(user1.getId(), user1.getFirstName(), user1.getLastName(), user1.getBirthdate(), user1.getEmail()));
//        when(userMapper.toDto(user2)).thenReturn(new UserDto(user2.getId(), user2.getFirstName(), user2.getLastName(), user2.getBirthdate(),user2.getEmail()));
//
//        List<UserDto> result = userController.findUsersByEmailContainingIgnoreCase("gmail");
//        assertEquals(users.size(), result.size());
//    }
//}