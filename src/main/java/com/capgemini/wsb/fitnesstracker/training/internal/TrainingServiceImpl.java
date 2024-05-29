package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: Provide Impl
/**
 Implementacja serwisu, operacje CRUD na treningach
 */
@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        try {
            return trainingRepository.findById(trainingId);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Training not finished yet ", e);
        }
    }

    /**
     Pobranei wsystkich treningów
     */
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    /**
     Utworzenie nowego treningu
     */
    public Training newTrening(NewTrainingDTO newTrainingDTO, User user) {
        Training training = trainingMapper.toEntity(newTrainingDTO, user);
        return trainingRepository.save(training);
    }

    /**
     * Pobranie treningu po Id usera
     */
    public List<TrainingDTO> getTrainingsByUserId (Long userId) {
        return trainingRepository.findByUserId(userId).stream().map(trainingMapper::trainingDTO).collect(Collectors.toList());
    }

    /**
     * Pobranie zakończonych treningów po dacie
     */
    public List<TrainingDTO> findCompletedTrainings(Date date) {
        return trainingRepository.findByEndTimeAfter(date).stream().map(trainingMapper::trainingDTO).collect(Collectors.toList());
    }

    /**
     * Pobranie treningów po rodzaju aktywności
     */
    public List<TrainingDTO> getTrainingsByActivity (ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType).stream().map(trainingMapper::trainingDTO).collect(Collectors.toList());
    }


    /**
     Aktualizacja wybranego parametru treningu
     */
    public Training updateTraining(Long id, NewTrainingDTO trainingUpdateDTO, User user) {
        return trainingRepository.findById(id)
                .map(training -> {
                    training.setStartTime(trainingUpdateDTO.getStartTime());
                    training.setEndTime(trainingUpdateDTO.getEndTime());
                    training.setActivityType(trainingUpdateDTO.getActivityType());
                    training.setDistance(trainingUpdateDTO.getDistance());
                    training.setAverageSpeed(trainingUpdateDTO.getAverageSpeed());
                    training.setUser(user);
                    return trainingRepository.save(training);
                })
                .orElseThrow(() -> new TrainingNotFoundException(id));
    }
}
