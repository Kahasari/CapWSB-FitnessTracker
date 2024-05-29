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

public class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;


    /**
     * Pobranie listy użytkowników.
     * Zwrócenie listy obiektów DTO reprezentujących użytkowników.
     *
     * @return ResponseEntity zawierający listę UserDto
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAllUsers().stream().map(userMapper::toDto).toList();
        return ResponseEntity.ok(users);
    }

    /**
     * Zwrócenie podsumowania użytkownika (id + imię).
     *
     * @return Lista UserDto z podstawowymi informacjami o użytkownikach
     */
    @GetMapping("/simple")
    public List<UserDto> getAllUserSummaries() {
        List<User> summaries = userService.findAllUsers();
        return summaries.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Dodanie kolejnego użytkownika.
     *
     * @param userDto dane użytkownika który ma być dodany
     * @return ResponseEntity zawierający dodany obiekt UserDto
     */
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody @Valid UserDto userDto) {
        User newUser = userService.newUser(userMapper.toEntity(userDto));
        return new ResponseEntity<>(userMapper.toDto(newUser), CREATED);
    }

    /**
     * Pobranie szczegółów danego użytkownika.
     *
     * @param id identyfikator użytkownika
     * @return ResponseEntity zawierający znaleziony obiekt UserDto
     * @throws UserNotFoundException jeśli użytkownik o podanym ID nie zostanie znaleziony
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
    /**
     * Usuwanie użytkownika.
     *
     * @param id identyfikator użytkownika do usunięcia z bazy
     * @return ResponseEntity bez zawartości
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    /**
     * Aktualizacja danych użytkownika.
     *
     * @param id identyfikator użytkownika do aktualizacji
     * @param userDto zaktualizowane dane użytkownika
     * @return ResponseEntity zawierający obiekt UserDto po aktualizacji
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> upToDateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(id, userMapper.toEntity(userDto));
        return new ResponseEntity<>(userMapper.toDto(updatedUser), ACCEPTED);
    }

    /**
     * Wyszukiwanie użytkownika po adresie e-mail.
     *
     * @param eMail adres e-mail do wyszukania
     * @return ResponseEntity zawierający listę UserDto pasujących do podanego e-maila
     */
    @GetMapping("/email")
    public ResponseEntity<List<UserDto>> findUserByEmail(@RequestParam("email") String eMail) {
        return userService.getUserByEmail(eMail)
                .map(userMapper::toDto)
                .map(user -> ResponseEntity.ok(List.of(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Wyszukiwanie użytkownika starszego niż zadany wiek
     *
     * @param time data, na podstawie której wyliczany jest wiek
     * @return ResponseEntity zawierający listę UserDto starszych niż zadany wiek
     */
    @GetMapping("/older/{time}")
    public ResponseEntity<List<UserDto>> searchForUsersOlderThan(@PathVariable LocalDate time) {
        int age = Period.between(time, LocalDate.now()).getYears();
        List<UserDto> users = userService.findUsersOlderThanX(age).stream().map(userMapper::toDto).toList();
        return ResponseEntity.ok(users);
    }

    /**
     * Wyszukiwanie użytkownika po częściowo znanym adresie e-mail.
     *
     * @param email część adresu e-mail do wyszukania
     * @return ResponseEntity zawierający listę UserDto pasujących do podanego kawałka e-maila
     */
    @GetMapping("/email/ifSearch")
    public ResponseEntity<List<UserDto>> findUsersByEmailContainingIgnoreCase (@RequestParam String email) {
        List<UserDto> users = userService.findUsersByEmailContainingIgnoreCase(email).stream().map(userMapper::toDto).toList();
        return ResponseEntity.ok(users);
    }
}






