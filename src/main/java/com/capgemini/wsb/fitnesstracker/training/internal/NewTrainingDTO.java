package com.capgemini.wsb.fitnesstracker.training.internal;

import lombok.Value;

import java.util.Date;

@Value
public class NewTrainingDTO {
    Long id;
    Long userId;
    Date startTime;
    Date endTime;
    ActivityType activityType;
    double distance;
    double averageSpeed;
}
