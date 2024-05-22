package com.capgemini.wsb.fitnesstracker.training;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingController;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class TrainingControllerTest {

    private TrainingServiceImpl trainingService;
    private TrainingController trainingController;
    private User mockUser;

    @BeforeEach
    void setUp() {
        trainingService = Mockito.mock(TrainingServiceImpl.class);
        trainingController = new TrainingController(trainingService);
    }

    @Test
    void createTraining() {
        Training training = new Training(mockUser, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
        when(trainingService.newTrening(training)).thenReturn(training);
        Training result = trainingController.createTraining(training);
        assertEquals(training, result);
    }

    @Test
    void getAllTrainings() {
        Training training = new Training(mockUser, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
        List<Training> trainings = Collections.singletonList(training);
        when(trainingService.getAllTrainings()).thenReturn(trainings);

        List<Training> result = trainingController.getAllTrainings();
        assertEquals(trainings.size(), result.size());
        assertEquals(trainings.get(0), result.get(0));
    }

    @Test
    void getTrainingsByUserId() {
        Training training = new Training(mockUser, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
        List<Training> trainings = Collections.singletonList(training);
        when(trainingService.getTrainingsByUserId(1L)).thenReturn(trainings);

        List<Training> result = trainingController.getTrainingsByUserId(1L);
        assertEquals(trainings.size(), result.size());
        assertEquals(trainings.get(0), result.get(0));
    }

    @Test
    void getCompletedTrainings() {
        Training training = new Training(mockUser, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
        List<Training> trainings = Collections.singletonList(training);
        when(trainingService.getFinishedTrainings(Mockito.any(Date.class))).thenReturn(trainings);

        List<Training> result = trainingController.getCompletedTrainings(new Date());
        assertEquals(trainings.size(), result.size());
        assertEquals(trainings.get(0), result.get(0));
    }

    @Test
    void getTrainingsByActivityType() {
        Training training = new Training(mockUser, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
        List<Training> trainings = Collections.singletonList(training);
        when(trainingService.getTrainingsByActivity(ActivityType.RUNNING)).thenReturn(trainings);

        List<Training> result = trainingController.getTrainingsByActivityType(ActivityType.RUNNING);
        assertEquals(trainings.size(), result.size());
        assertEquals(trainings.get(0), result.get(0));
    }

    @Test
    void updateTraining() {
        Training training = new Training(mockUser, new Date(), new Date(), ActivityType.SWIMMING, 15.0, 6.0);
        when(trainingService.updateTraining(1L, training)).thenReturn(training);

        Training result = trainingController.updateTraining(1L, training);
        assertEquals(training, result);
    }

    @Test
    void updateTraining_NotFound() {
        Training training = new Training(mockUser, new Date(), new Date(), ActivityType.SWIMMING, 15.0, 6.0);
        when(trainingService.updateTraining(1L, training)).thenThrow(new TrainingNotFoundException(1L));

        assertThrows(TrainingNotFoundException.class, () -> trainingController.updateTraining(1L, training));
    }
}

