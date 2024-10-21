package com.example.healthhub.workout;

import com.example.healthhub.client.Client;
import com.example.healthhub.exercise.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.healthhub.exercise.Exercise;
import com.example.healthhub.exercise.ExerciseService;


import java.util.Optional;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;
    
    @Autowired
    private ExerciseService exerciseService;

    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    public Optional<Workout> getWorkoutById(Long id) {
        return workoutRepository.findById(id);
    }

    public Iterable<Workout> getWorkoutsByClient(Client client) {
        return workoutRepository.findByClient(client);
    }

    public Workout updateWorkout(Long id, Workout workout) {
        Optional<Workout> workoutOptional = workoutRepository.findById(id);
        if (workoutOptional.isPresent()) {
            Workout workoutToUpdate = workoutOptional.get();
            workoutToUpdate.setName(workout.getName());
            workoutToUpdate.setClient(workout.getClient());
            return workoutRepository.save(workoutToUpdate);
        } else {
            throw new RuntimeException("Workout not found");
        }
    }

    public Optional<Workout> getWorkoutByIdAndClient(Long id, Client client) {
        return workoutRepository.findByIdAndClient(id, client);
    }


    public Iterable<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }


    public Workout addExerciseToWorkout(Long workoutId, String exerciseName) {
        Optional<Workout> workoutOptional = workoutRepository.findById(workoutId);
        Workout workout = workoutOptional.orElseThrow(() -> new RuntimeException("Workout not found"));

        Optional<Exercise> exerciseOptional = exerciseRepository.findByName(exerciseName);
        if (exerciseOptional.isPresent()) {
            Exercise exercise = exerciseOptional.get();
            if (exercise.getWorkoutId() != null && exercise.getWorkoutId().equals(workoutId)) {
                throw new RuntimeException("El ejercicio ya está añadido a esta rutina.");
            }
        }

        exerciseService.addExerciseToWorkout(exerciseName, workoutId);

        workout = workoutRepository.findById(workoutId).orElseThrow(() -> new RuntimeException("Workout not found"));
        return workout;
    }

    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }
}