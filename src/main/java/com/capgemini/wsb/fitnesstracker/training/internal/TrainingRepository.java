package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    //wyszukiwanie treningów dla określonego Użytkownika:
    List<Training> findByUserId(Long userId);

    // wyszukiwanie wszystkich treningów zakończonych (po konkretnej zdefiniowanej dacie)
    List<Training> findByEndTimeAfter (Date date);

    // Wyszukiwanie po rodzaju treningu
    List<Training> findByActivityType (ActivityType activityType);
}
