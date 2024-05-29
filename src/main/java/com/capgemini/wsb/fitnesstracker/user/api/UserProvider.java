package com.capgemini.wsb.fitnesstracker.user.api;

import java.util.List;
import java.util.Optional;

public interface UserProvider {

    /**
     * Pobiera użytkownika na podstawie jego id
     * Jeśli użytkownik o podanym id nie zostanie znaleziony, zostanie zwrócony {@link Optional#empty()}.
     *
     * @param userId id użytkownika do wyszukania
     * @return {@link Optional} zawierający znalezionego użytkownika lub {@link Optional#empty()} jeśli nie znaleziono
     */
    Optional<User> getUser(Long userId);

    /**
     * Pobiera użytkownika na podstawie jego adresu e-mail.
     * Jeśli użytkownik o podanym adresie e-mail nie istnieje zwróć {@link Optional#empty()}.
     *
     * @param email adres e-mail użytkownika do wyszukania
     * @return {@link Optional} zawierający znalezionego użytkownika lub {@link Optional#empty()} jeśli nie istnieje
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Pobiera wszystkich użytkowników.
     *
     * @return lista wszystkich użytkowników
     */
    List<User> findAllUsers();

    /**
     * Pobiera użytkowników starszych niż zadany wiek.
     *
     * @param age wiek do porównania
     * @return lista użytkowników starszych niż zadany wiek
     */

    List<User> findUsersOlderThanX (int age);

    /**
     * Pobiera użytkowników, których adres e-mail zawiera część zadanego "stringa"
     *
     * @param email część adresu e-mail do wyszukania
     * @return lista użytkowników z adresami e-mail zawierającymi podanego "stringa"
     */
    List<User> findUsersByEmailContainingIgnoreCase(String email);


}
