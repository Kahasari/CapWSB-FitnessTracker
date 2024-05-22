package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

// TODO: Provide Impl
/**
 Implementacja serwisu, operacje CRUD na treningach
 */
@Service
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        try {
            return trainingRepository.findById(trainingId);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Training not finished yet ", e);
        }
    }

    /**
     Utworzenie nowego treningu
     */
    public Training newTrening (Training training) {
        return trainingRepository.save(training);
    }

    /**
     Pobranei wsystkich treningów
     */
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    /**
     Pobranie treningu po Id usera
     */
    public List<Training> getTrainingsByUserId (Long userId) {
        return trainingRepository.findByUserId(userId);
    }

    /**
     Pobranie zakończonych treningów po dacie
     */
    public List<Training> getFinishedTrainings (Date date) {
        return trainingRepository.findByEndTimeBefore(date);
    }

    /**
     Pobranie treningów po rodzaju aktywności
     */
    public List<Training> getTrainingsByActivity (ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }
    /**
     Aktualizacja wybranego parametru treningu
     */
    public Training updateTraining (Long id, Training trainingToUpdate) {
        return trainingRepository.findById(id).map(training -> {
            training.setStartTime(trainingToUpdate.getStartTime());
            training.setEndTime(trainingToUpdate.getStartTime());
            training.setActivityType(trainingToUpdate.getActivityType());
            training.setDistance(trainingToUpdate.getDistance());
            training.setAverageSpeed(trainingToUpdate.getAverageSpeed());
            return trainingRepository.save(training);
        }).orElseThrow(() -> new TrainingNotFoundException(id));
    }
}
