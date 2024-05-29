package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.user.internal.UserSummaryDTO;

import java.util.List;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService extends UserProvider{

    User newUser(User user);
    List<UserSummaryDTO> getAllUserSummaries();
    void deleteUser (Long userId);
    User updateUser(Long userId, User updatedUser);

}

