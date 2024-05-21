package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
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
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    /**
     Utworzenie nowego użytkownika
     */
    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

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

    // Wprowadzenie dodatkowych metod

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
    public void deleteUser (Long userId) {
        log.info("User with ID {} was deleted", userId);
        // Dodatkowa weryfikacja czy użytkownik od danym ID w ogóle istniał
        if (!userRepository.existsById(userId)) {
            // Wyrzucenie wyjątku
            throw new IllegalArgumentException("There is no user with " + userId + " ID");
        }
        userRepository.deleteById(userId);
    }

    /**
     Aktualizacja danych/parametrów wybranego użytkownika
     */
    public User updateUser(Long userId, User updatedUser) {
        log.info("User with ID {} is going to be updated", userId);
        User temporaryUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("There is no user with " + userId + " ID"));
        temporaryUser.setFirstName(updatedUser.getFirstName());
        temporaryUser.setLastName(updatedUser.getLastName());
        temporaryUser.setBirthdate(updatedUser.getBirthdate());
        temporaryUser.setEmail(updatedUser.getEmail());
        return userRepository.save(temporaryUser);
    }

    /**
     Wyszukiwanie danego użytkownika jeśli jest starszy niż dany wiek
     */
    public List<User> findUsersOlderThanX (int age) {
        LocalDate localDate = LocalDate.now();
        return userRepository.findAll().stream().filter(user -> {
            LocalDate dateOfBirth = user.getBirthdate();
            return Period.between(dateOfBirth, localDate).getYears() > age;
        }).collect(Collectors.toList());
    }

    /**
     * -- Dodatkowa metoda
     Wyszukiwanie użytkownika po częściowo znanym adresie e-mail, niepełnym
     */
    public List<User> findUsersByEmailContainingIgnoreCase(String email) {
        return userRepository.findAll().stream().filter(user ->
                user.getEmail().toLowerCase().contains(email.toLowerCase())).
                collect(Collectors.toList());
    }
}