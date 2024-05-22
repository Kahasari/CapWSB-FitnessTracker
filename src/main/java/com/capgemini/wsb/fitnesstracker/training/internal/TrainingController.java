package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 Obsłua operacji HTTP związanych z użytkownikami
 */
@RestController
@RequestMapping("/trainings")
public class TrainingController {
    private final TrainingServiceImpl trainingService;

    public TrainingController(TrainingServiceImpl trainingService) {
        this.trainingService = trainingService;
    }

    /**
     Utworzenie nowego treningu
     */
    @PostMapping
    public Training createTraining(@RequestBody Training training) {
        return trainingService.newTrening(training);
    }

    /**
     Pobranie wszystkich treningów
     */
    @GetMapping
    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    /**
     Pobranie treningów po id konkretnego usera
     */
    @GetMapping("/user/{userId}")
    public List<Training> getTrainingsByUserId(@PathVariable Long userId) {
        return trainingService.getTrainingsByUserId(userId);
    }

    /**
     Pobranie ukończonych już treningów
     */
    @GetMapping("/completed")
    public List<Training> getCompletedTrainings(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return trainingService.getFinishedTrainings(date);
    }

    /**
     Pobranie treningów po rodzaju aktywności/treningu
     */
    @GetMapping("/activity/{activityType}")
    public List<Training> getTrainingsByActivityType(@PathVariable ActivityType activityType) {
        return trainingService.getTrainingsByActivity(activityType);
    }

    /**
     Aktualizacja danego treningu / Zmiana
     */
    @PutMapping("/{id}")
    public Training updateTraining(@PathVariable Long id, @RequestBody Training updatedTraining) {
        Optional<Training> updated = Optional.ofNullable(trainingService.updateTraining(id, updatedTraining));
        return updated.orElse(null);
    }
}

