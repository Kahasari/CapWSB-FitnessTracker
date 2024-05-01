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
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> Objects.equals(user.getEmail(), email))
                        .findFirst();
    }

    /**
    Wyszukiwanie użytkowników na podstawie dopasowanie (cześciowo) e-maila, z pominięciem wielkości liter
    */
    List<User> findByEmailContainingIgnoreCase(String email);

}
