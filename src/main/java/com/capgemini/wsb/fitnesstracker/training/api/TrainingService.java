package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.NewTrainingDTO;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingDTO;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.Date;
import java.util.List;

public interface TrainingService extends TrainingProvider{
    /**
     * Utworzenie nowy trening.
     *
     * @param newTrainingDTO dane nowego treningu
     * @param user użytkownik powiązany z treningiem
     * @return utworzony trening
     */
    public Training newTrening (NewTrainingDTO newTrainingDTO, User user);
    /**
     * Pobiera treningi na podstawie id użytkownika.
     *
     * @param userId id użytkownika
     * @return lista {@link TrainingDTO} zawierająca treningi użytkownika
     */
    List<TrainingDTO> getTrainingsByUserId(Long userId);
    /**
     * Pobiera zakończone treningi po dacie.
     *
     * @param date data zakończenia treningu
     * @return lista {@link TrainingDTO} zawierająca zakończone treningi
     */
    public List<TrainingDTO> findCompletedTrainings (Date date);
    /**
     * Pobiera treningi na podstawie rodzaju aktywności.
     *
     * @param activityType rodzaj aktywności
     * @return lista {@link TrainingDTO} zawierająca treningi o danym typie ćwiczenia
     */
    public List<TrainingDTO> getTrainingsByActivity (ActivityType activityType);
    /**
     * Aktualizuje wybrany trening.
     *
     * @param id id treningu do zaktualizowania
     * @param newTrainingDTO dane do aktualizacji treningu
     * @param user użytkownik powiązany z treningiem
     * @return zaktualizowany trening
     */
    public Training updateTraining (Long id, NewTrainingDTO newTrainingDTO, User user);
}

