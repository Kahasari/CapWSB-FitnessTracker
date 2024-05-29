package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 Repo obsługujące operację dostępu do danych użytkowników, operacje CRUD + wyszukiwanie użytkowników
 */

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Wyszukiwanie użytkowników na podstawie email, dopasowanie jeden do jednego
     *
     * @param email adres e-mail użytkownika do wyszukania
     * @return {@link Optional} zawierający znalezionego użytkownika lub {@link Optional#empty()} jeśli żaden nie został znaleziony
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    /**
     * Wyszukiwanie użytkowników na podstawie częściowego dopasowania adresu e-mail, z pominięciem wielkości liter.
     *
     * @param email część adresu e-mail do wyszukania
     * @return Lista użytkowników których email zawiera zadanego "stringa", wielkość liter bez znaczenia
     */
    List<User> findByEmailContainingIgnoreCase(String email);

}
