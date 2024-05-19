package com.example.healthhub.exercise;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ExerciseService {
    
    @Autowired
    ExerciseRepository exerciseRepository;

    
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public ArrayList<Exercise> getExercises() {
        return (ArrayList<Exercise>) exerciseRepository.findAll();
    }

    public Optional<Exercise> getExerciseById(Long id) {
        return exerciseRepository.findById(id);
    }

    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public Boolean deleteExercise(Long id) {
        try {
            exerciseRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Exercise updateExercise(Exercise request, Long id) {
        Optional <Exercise> exercise = exerciseRepository.findById(id);
        if (exercise.isPresent()) {
            Exercise exerciseToSave= exercise.get();
            exerciseToSave.setName(request.getName());
            exerciseToSave.setType(request.getType());
            exerciseToSave.setRepetitions(request.getRepetitions());
            exerciseToSave.setSets(request.getSets());
            return exerciseRepository.save(exerciseToSave);
        } else {
            return null;
        }
    }
}
