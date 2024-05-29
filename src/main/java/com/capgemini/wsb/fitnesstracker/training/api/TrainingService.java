package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.NewTrainingDTO;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingDTO;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.Date;
import java.util.List;

public interface TrainingService extends TrainingProvider{
    /**
     *
     * @param newTrainingDTO
     * @return createdTraining
     */
    public Training newTrening (NewTrainingDTO newTrainingDTO, User user);
    List<TrainingDTO> getTrainingsByUserId(Long userId);
    public List<TrainingDTO> findCompletedTrainings (Date date);
    public List<TrainingDTO> getTrainingsByActivity (ActivityType activityType);
    public Training updateTraining (Long id, NewTrainingDTO newTrainingDTO, User user);
}

