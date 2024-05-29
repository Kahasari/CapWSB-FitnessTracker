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
     Pobranie danych użytkownika na podstawie wprowadzonego identyfikatora
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     Pobranie danych użytkownika na podstawie wprowadzonego adresu e-mail,
     czyli wyświetlenie danych użytkownika, który posiada konkretny adres e-mail.
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     Wyświetlenie wszystkich użytkowników w bazie
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    /**
     Utworzenie nowego użytkownika
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
     Wylistowanie podstawowych informacji o wszystkich użytkownikach, w tym przypadku wyłącznie ID + nazwa klienta
     */
    // Zaktualizowano pod wytyczne, wcześniejs metoda zwracała ID + imię + nazwisko
    // Powinna zwracać Id + imie, tak jak w przygotowanym rekordzie UserSummaryDTO.
    public List<UserSummaryDTO> getAllUserSummaries() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserSummaryDTO(user.getId(), user.getFirstName()))
                .collect(Collectors.toList());
    }

    /**
     Usunięcie z bazy użytkownika o konkretnym ID
     */
    public void deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("There is no user with " + userId + " ID");
        }
    }

    /**
     Aktualizacja danych/parametrów wybranego użytkownika
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
     Wyszukiwanie danego użytkownika jeśli jest starszy niż dany wiek
     */
    public List<User> findUsersOlderThanX(int age) {
        LocalDate localDate = LocalDate.now();
        return userRepository.findAll().stream()
                .filter(user -> Period.between(user.getBirthdate(), localDate).getYears() > age)
                .collect(Collectors.toList());
    }

    /**
     * -- Dodatkowa metoda
     Wyszukiwanie użytkownika po częściowo znanym adresie e-mail, niepełnym
     */
    public List<User> findUsersByEmailContainingIgnoreCase(String email) {
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(email.toLowerCase()))
                .collect(Collectors.toList());
    }
}