package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
     Zwrócenie podsumowania użytkownika (id + imię)
     */
    @GetMapping("/summary")
    public List<UserSummaryDTO> getAllUserSummaries() {
        return userService.getAllUserSummaries();
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

    /**
     * Pobranie szczegółów danego użytkownika
     */
    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUser(id);
        return user.map(userMapper::toDto).orElseThrow(() -> new UserNotFoundException(id));
    }
    /**
     * Usuwanie użytkownika
     */
    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    /**
     * Zaktualizowanie danych użytkownika
     */
    @PutMapping("/{id}")
    public UserDto upToDateUser(@PathVariable Long id, @RequestBody User userDto) {
        return userMapper.toDto(userService.updateUser(id, userDto));
    }

    /**
     * Wyszukiwanie użytkownika po e-mailu
     */
    @GetMapping("/search")
    public Optional<User> findUserByEmail(String eMail) {
        return userService.getUserByEmail(eMail);
    }

    /**
     * Wyszukiwanie użytkownika starszego niż zadany parametr/wiek
     */
    @GetMapping("/age")
    public List<UserDto> searchForUsersOlderThanX (int age) {
        return userService.findUsersOlderThanX(age).stream().map(userMapper::toDto).toList();
    }

    /**
     * -- Dodatkowa metoda
     Wyszukiwanie użytkownika po częściowo znanym adresie e-mail, niepełnym
     */
    @GetMapping("/search/ifForget")
    public List<UserDto> findUsersByEmailContainingIgnoreCase (@RequestParam String email) {
        return userService.findUsersByEmailContainingIgnoreCase(email).
                stream().map(userMapper::toDto).toList();
    }
}