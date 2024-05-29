package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

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
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAllUsers().stream().map(userMapper::toDto).toList();
        return ResponseEntity.ok(users);
    }

    /**
     * Zwrócenie podsumowania użytkownika (id + imię)
     */

    @GetMapping("/simple")
    public List<UserDto> getAllUserSummaries() {
        List<User> summaries = userService.findAllUsers();
        return summaries.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    /**
     Dodanie kolejnego użytkownika
     */
//    @PostMapping
//    public ResponseEntity<User> addUser(@RequestBody @Valid User userDto) {
//
//        // Demonstracja how to use @RequestBody
//        System.out.println("User with e-mail: " + userDto.getEmail() + "passed to the request");
//
//        // TODO: saveUser with Service and return User
//        User savedUser = userService.newUser(userDto);
//        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody @Valid UserDto userDto) {
        User newUser = userService.newUser(userMapper.toEntity(userDto));
        return new ResponseEntity<>(userMapper.toDto(newUser), CREATED);
    }

    /**
     * Pobranie szczegółów danego użytkownika
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
    /**
     * Usuwanie użytkownika
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    /**
     * Zaktualizowanie danych użytkownika
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> upToDateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(id, userMapper.toEntity(userDto));
        return new ResponseEntity<>(userMapper.toDto(updatedUser), ACCEPTED);
    }

    /**
     * Wyszukiwanie użytkownika po e-mailu
     */
    @GetMapping("/email")
    public ResponseEntity<List<UserDto>> findUserByEmail(@RequestParam("email") String eMail) {
        return userService.getUserByEmail(eMail)
                .map(userMapper::toDto)
                .map(user -> ResponseEntity.ok(List.of(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Wyszukiwanie użytkownika starszego niż zadany parametr/wiek
     */
    @GetMapping("/older/{time}")
    public ResponseEntity<List<UserDto>> searchForUsersOlderThan(@PathVariable LocalDate time) {
        int age = Period.between(time, LocalDate.now()).getYears();
        List<UserDto> users = userService.findUsersOlderThanX(age).stream().map(userMapper::toDto).toList();
        return ResponseEntity.ok(users);
    }

    /**
     * -- Dodatkowa metoda
     Wyszukiwanie użytkownika po częściowo znanym adresie e-mail, niepełnym
     */
    @GetMapping("/email/ifSearch")
    public ResponseEntity<List<UserDto>> findUsersByEmailContainingIgnoreCase (@RequestParam String email) {
        List<UserDto> users = userService.findUsersByEmailContainingIgnoreCase(email).stream().map(userMapper::toDto).toList();
        return ResponseEntity.ok(users);
    }
}






