package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;
import lombok.Value;

import java.util.Date;

@Value
public class TrainingDTO {
    Long id;
    UserDto user;
    Date startTime;
    Date endTime;
    ActivityType activityType;
    double distance;
    double averageSpeed;

    public UserDto getUser() {
        return user;
    }
}
