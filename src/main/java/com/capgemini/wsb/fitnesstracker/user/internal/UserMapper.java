package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

/**
 * Mapper do konwersji pomiędzy encją User a DTO UserDto.
 */
@Component
class UserMapper {
    UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }
    User toEntity(@Valid UserDto userDto) {
        return new User(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getBirthdate(),
                userDto.getEmail());
    }

}
