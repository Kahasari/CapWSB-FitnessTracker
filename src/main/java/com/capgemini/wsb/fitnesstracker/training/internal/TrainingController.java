package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 Obsłua operacji HTTP związanych z użytkownikami
 */
@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;
    private final UserProvider userProvider;

    /**
     * Utworzenie nowego treningu.
     *
     * @param newTrainingDTO dane nowego treningu
     * @return ResponseEntity zawierający utworzony obiekt TrainingDTO
     */
    @PostMapping
    public ResponseEntity<TrainingDTO> newTraining(@RequestBody NewTrainingDTO newTrainingDTO) {
        if (newTrainingDTO.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        User user = userProvider.getUser(newTrainingDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(newTrainingDTO.getUserId()));
        Training savedTraining = trainingService.newTrening(newTrainingDTO, user);
        return new ResponseEntity<>(trainingMapper.trainingDTO(savedTraining), HttpStatus.CREATED);
    }

    /**
     * Pobranie wszystkich treningów.
     *
     * @return lista wszystkich treningów
     */
    @GetMapping
    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    /**
     * Pobranie treningów po id użytkownika.
     *
     * @param userId idr użytkownika
     * @return lista TrainingDTO zawierająca treningi użytkownika
     */
    @GetMapping("/{userId}")
    public List<TrainingDTO> getTrainingsByUserId(@PathVariable Long userId) {
        return trainingService.getTrainingsByUserId(userId);
    }

    /**
     * Pobranie zakończonych treningów po dacie.
     *
     * @param date data zakończenia treningu
     * @return lista TrainingDTO zawierająca zakończone treningi po danej dacie
     */
    @GetMapping("/finished/{date}")
    public List<TrainingDTO> getCompletedTrainings(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return trainingService.findCompletedTrainings(date);
    }

    /**
     * Pobranie treningów po rodzaju aktywności.
     *
     * @param activityType rodzaj aktywności
     * @return lista TrainingDTO zawierająca treningi o danym typie aktywności
     */
    @GetMapping("/activityType")
    public List<TrainingDTO> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return trainingService.getTrainingsByActivity(activityType);
    }

    /**
     * Aktualizacja wybranego treningu.
     *
     * @param id id treningu do zaktualizowania
     * @param newTrainingDTO dane do aktualizacji treningu
     * @return ResponseEntity zawierający zaktualizowany obiekt TrainingDTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<TrainingDTO> updateTraining(@PathVariable Long id, @RequestBody NewTrainingDTO newTrainingDTO) {
        if (newTrainingDTO.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        User user = userProvider.getUser(newTrainingDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(newTrainingDTO.getUserId()));
        Training updatedTraining = trainingService.updateTraining(id, newTrainingDTO, user);
        return ResponseEntity.status(HttpStatus.OK).body(trainingMapper.trainingDTO(updatedTraining));
    }
}

