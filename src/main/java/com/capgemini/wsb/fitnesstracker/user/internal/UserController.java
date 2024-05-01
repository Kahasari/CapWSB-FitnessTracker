package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
Obsłua operacji HTTP związanych z użytkownikami
*/
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor

class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;


/**
Pobranie listy użytkowników
Zwrócenie listy obiektów DTO reprezentujących użytkowników
*/
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }
/**
Dodanie kolejnego użytkownika
 */
    @PostMapping
    public User addUser(@RequestBody User userDto) {

        // Demonstracja how to use @RequestBody
        System.out.println("User with e-mail: " + userDto.getEmail() + "passed to the request");

        // TODO: saveUser with Service and return User
        return userService.createUser(userDto);
    }

}