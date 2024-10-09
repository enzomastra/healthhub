package com.example.healthhub.workout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    // Guardar una rutina en la base de datos
    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    // Buscar una rutina por ID
    public Optional<Workout> getWorkoutById(Long id) {
        return workoutRepository.findById(id);
    }
}
