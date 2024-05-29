package com.capgemini.wsb.fitnesstracker.user.internal;

import lombok.Data;

import java.time.LocalDate;
@Data

public class UserDto {
    Long id;
    String firstName;
    String lastName;
    LocalDate birthdate;
    String email;

    public UserDto(Long id, String firstName, String lastName, LocalDate birthdate, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
    }
}
