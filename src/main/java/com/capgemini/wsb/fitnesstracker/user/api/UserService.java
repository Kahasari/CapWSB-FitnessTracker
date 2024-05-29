package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.user.internal.UserSummaryDTO;

import java.util.List;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService extends UserProvider {

    /**
     * Utworzenie użytkownika.
     *
     * @param user encja użytkownika do utworzenia
     * @return utworzona encja użytkownika
     */
    User newUser(User user);

    /**
     * Pobiera podsumowanie wszystkich użytkowników.
     *
     * @return lista {@link UserSummaryDTO} zawierająca podstawowe informacje o użytkownikach
     */
    List<UserSummaryDTO> getAllUserSummaries();

    /**
     * Usuwa użytkownika na podstawie jego Id.
     *
     * @param userId Id użytkownika do usunięcia
     */
    void deleteUser (Long userId);

    /**
     * Aktualizuje dane użytkownika w bazie.
     *
     * @param userId identyfikator użytkownika
     * @param updatedUser zaktualizowana encja użytkownika
     * @return zaktualizowana encja użytkownika
     */
    User updateUser(Long userId, User updatedUser);

}

