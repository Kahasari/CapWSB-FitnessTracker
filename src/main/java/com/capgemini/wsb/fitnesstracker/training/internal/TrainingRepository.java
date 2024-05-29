package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Wyszukiwanie treningów dla określonego użytkownika.
     *
     * @param userId identyfikator użytkownika
     * @return lista treningów użytkownika
     */
    List<Training> findByUserId(Long userId);

    /**
     * Wyszukiwanie wszystkich treningów zakończonych po konkretnej dacie.
     *
     * @param date data zakończenia treningu
     * @return lista zakończonych treningów po dacie
     */
    List<Training> findByEndTimeAfter (Date date);

    /**
     * Wyszukiwanie treningów po rodzaju aktywności.
     *
     * @param activityType rodzaj aktywności
     * @return lista treningów o danej aktywności
     */
    List<Training> findByActivityType (ActivityType activityType);
}
