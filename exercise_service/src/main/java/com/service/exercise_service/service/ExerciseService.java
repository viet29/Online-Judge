package com.service.exercise_service.service;

import com.service.exercise_service.model.Exercise;

import java.util.List;
import java.util.Optional;

public interface ExerciseService {

    Optional<Exercise> getExerciseByCode(String code);

    List<Exercise> getAllExercises();
}
