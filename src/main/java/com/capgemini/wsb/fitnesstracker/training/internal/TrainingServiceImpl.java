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

    /**
     * Pobranie treningu na podstawie id
     *
     * @param trainingId id treningu
     * @return {@link Optional} zawierający znaleziony trening lub {@link Optional#empty()} jeśli nie znaleziono
     */

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        try {
            return trainingRepository.findById(trainingId);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Training not finished yet ", e);
        }
    }

    /**
     * Pobranie wszystkich treningów.
     *
     * @return lista wszystkich treningów
     */
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    /**
     * Utworzenie nowego treningu.
     *
     * @param newTrainingDTO dane nowego treningu
     * @param user użytkownik powiązany z treningiem
     * @return utworzony trening
     */
    public Training newTrening(NewTrainingDTO newTrainingDTO, User user) {
        Training training = trainingMapper.toEntity(newTrainingDTO, user);
        return trainingRepository.save(training);
    }

    /**
     * Pobranie treningów na podstawie id użytkownika.
     *
     * @param userId id użytkownika
     * @return lista {@link TrainingDTO} zawierających treningi użytkownika
     */
    public List<TrainingDTO> getTrainingsByUserId (Long userId) {
        return trainingRepository.findByUserId(userId).stream().map(trainingMapper::trainingDTO).collect(Collectors.toList());
    }

    /**
     * Pobranie zakończonych treningów po dacie.
     *
     * @param date data końca treningu
     * @return lista {@link TrainingDTO} zawierających zakończone treningi
     */
    public List<TrainingDTO> findCompletedTrainings(Date date) {
        return trainingRepository.findByEndTimeAfter(date).stream().map(trainingMapper::trainingDTO).collect(Collectors.toList());
    }

    /**
     * Pobranie treningów na podstawie rodzaju aktywności.
     *
     * @param activityType typ aktywności
     * @return lista {@link TrainingDTO} zawierających treningi o danym rodzaju aktywności
     */
    public List<TrainingDTO> getTrainingsByActivity (ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType).stream().map(trainingMapper::trainingDTO).collect(Collectors.toList());
    }


    /**
     * Aktualizacja wybranego treningu.
     *
     * @param id id treningu do zaktualizowania
     * @param trainingUpdateDTO dane do aktualizacji
     * @param user użytkownik powiązany z treningiem
     * @return zaktualizowany trening
     * @throws TrainingNotFoundException jeśli trening o podanym id nie zostanie znaleziony
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
