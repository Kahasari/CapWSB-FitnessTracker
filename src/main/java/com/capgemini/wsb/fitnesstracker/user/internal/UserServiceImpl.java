package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 Implementacja serwisu, operacje CRUD na użytkownikach
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Pobranie danych użytkownika na podstawie Id
     *
     * @param userId identyfikator użytkownika (Id)
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Pobranie danych użytkownika na podstawie adresu e-mail,
     * wyświetlenie danych użytkownika, który ma dany email
     *
     * @param email adres e-mail użytkownika
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Wyświetlenie wszystkich użytkowników w bazie.
     *
     * @return Lista wszystkich użytkowników
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    /**
     * Utworzenie nowego użytkownika.
     *
     * @param user obiekt User do utworzenia
     * @return Utworzony obiekt User
     */
    @Override
    public User newUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    /**
     * Wypisanie kluczowych informacji o wszystkich użytkownikach, Id + imie + nazwisko
     *
     * @return Lista obiektów UserSummaryDTO zawierających podstawowe informacje o użytkownikach
     */
    public List<UserSummaryDTO> getAllUserSummaries() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserSummaryDTO(user.getId(), user.getFirstName()))
                .collect(Collectors.toList());
    }

    /**
     * Usunięcie z bazy użytkownika o konkretnym ID.
     *
     * @param userId identyfikator użytkownika do wyrzucenia z bazy
     */
    public void deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("There is no user with " + userId + " ID");
        }
    }

    /**
     * Aktualizacja danych użytkownika
     *
     * @param userId identyfikator użytkownika do zaktualizowania
     * @param updatedUser zaktualizowane dane użytkownika
     * @return Zaktualizowany obiekt User
     * @throws UserNotFoundException jeśli użytkownik o podanym ID nie istnieje w bazie
     */
    public User updateUser(Long userId, User updatedUser) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    user.setBirthdate(updatedUser.getBirthdate());
                    user.setEmail(updatedUser.getEmail());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * Wyszukiwanie danego użytkownika, jeśli jest starszy niż dany wiek.
     *
     * @param age wiek do porównania
     * @return Lista użytkowników starszych niż podany wiek
     */
    public List<User> findUsersOlderThanX(int age) {
        LocalDate localDate = LocalDate.now();
        return userRepository.findAll().stream()
                .filter(user -> Period.between(user.getBirthdate(), localDate).getYears() > age)
                .collect(Collectors.toList());
    }

    /**
    * Wyszukiwanie użytkownika po niepełnym adresie email
    *
    * @param email część adresu email do wyszukania
    * @return Lista użytkowników których email zawiera w sobie zadany "string", zignorowanie wielkości liter
    */
    public List<User> findUsersByEmailContainingIgnoreCase(String email) {
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(email.toLowerCase()))
                .collect(Collectors.toList());
    }
}