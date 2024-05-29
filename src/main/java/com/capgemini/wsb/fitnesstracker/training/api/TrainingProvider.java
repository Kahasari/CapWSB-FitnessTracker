package com.capgemini.wsb.fitnesstracker.training.api;

import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Pobiera trening na podstawie id.
     * Jeśli trening o podanym id nie zostanie znaleziony, zostanie zwrócony {@link Optional#empty()}.
     *
     * @param trainingId identyfikator treningu do wyszukania
     * @return {@link Optional} zawierający znaleziony trening lub {@link Optional#empty()} jeśli nie znaleziono
     */
    Optional<Training> getTraining(Long trainingId);
    /**
     * Pobiera wszystkie treningi.
     *
     * @return lista wszystkich treningów
     */
    List<Training> getAllTrainings();

}
