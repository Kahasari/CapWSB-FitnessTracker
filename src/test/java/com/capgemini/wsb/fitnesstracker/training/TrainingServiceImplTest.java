//package com.capgemini.wsb.fitnesstracker.training;
//
//import com.capgemini.wsb.fitnesstracker.training.api.Training;
//import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
//import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
//import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
//import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
//import com.capgemini.wsb.fitnesstracker.user.api.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//class TrainingServiceImplTest {
//
//    @Mock
//    private TrainingRepository trainingRepository;
//
//    @InjectMocks
//    private TrainingServiceImpl trainingService;
//    private User mockUser;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testNewTraining() {
//        Training training = new Training(mockUser, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
//        when(trainingRepository.save(any(Training.class))).thenReturn(training);
//
//        Training result = trainingService.newTrening(training);
//        assertNotNull(result);
//        assertEquals(training.getUser(), result.getUser());
//    }
//
//    @Test
//    void testGetTraining() {
//        Training training = new Training(mockUser, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
//        when(trainingRepository.findById(anyLong())).thenReturn(Optional.of(training));
//
//        Optional<Training> result = trainingService.getTraining(1L);
//        assertTrue(result.isPresent());
//        assertEquals(training.getUser(), result.get().getUser());
//    }
//
//    @Test
//    void testGetAllTrainings() {
//        Training training = new Training(mockUser, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
//        when(trainingRepository.findAll()).thenReturn(Collections.singletonList(training));
//
//        List<Training> result = trainingService.getAllTrainings();
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//        assertEquals(training.getUser(), result.get(0).getUser());
//    }
//
//    @Test
//    void testGetTrainingsByUserId() {
//        Training training = new Training(mockUser, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
//        when(trainingRepository.findByUserId(anyLong())).thenReturn(Collections.singletonList(training));
//
//        List<Training> result = trainingService.getTrainingsByUserId(1L);
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//        assertEquals(training.getUser(), result.get(0).getUser());
//    }
//
//    @Test
//    void testGetFinishedTrainings() {
//        Training training = new Training(mockUser, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
//        when(trainingRepository.findByEndTimeBefore(any(Date.class))).thenReturn(Collections.singletonList(training));
//
//        List<Training> result = trainingService.getFinishedTrainings(new Date());
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//        assertEquals(training.getUser(), result.get(0).getUser());
//    }
//
//    @Test
//    void testGetTrainingsByActivity() {
//        Training training = new Training(mockUser, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
//        when(trainingRepository.findByActivityType(any(ActivityType.class))).thenReturn(Collections.singletonList(training));
//
//        List<Training> result = trainingService.getTrainingsByActivity(ActivityType.RUNNING);
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//        assertEquals(training.getUser(), result.get(0).getUser());
//    }
//
//    @Test
//    void testUpdateTraining() {
//        Training existingTraining = new Training(mockUser, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
//        Training updatedTraining = new Training(mockUser, new Date(), new Date(), ActivityType.SWIMMING, 15.0, 6.0);
//        when(trainingRepository.findById(anyLong())).thenReturn(Optional.of(existingTraining));
//        when(trainingRepository.save(any(Training.class))).thenReturn(updatedTraining);
//
//        Training result = trainingService.updateTraining(1L, updatedTraining);
//        assertNotNull(result);
//        assertEquals(updatedTraining.getActivityType(), result.getActivityType());
//        assertEquals(updatedTraining.getDistance(), result.getDistance());
//    }
//
//    @Test
//    void testUpdateTraining_NotFound() {
//        Training updatedTraining = new Training(mockUser, new Date(), new Date(), ActivityType.SWIMMING, 15.0, 6.0);
//
//        when(trainingRepository.findById(anyLong())).thenReturn(Optional.empty());
//        assertThrows(TrainingNotFoundException.class, () -> trainingService.updateTraining(1L, updatedTraining));
//    }
//}
