package com.example.healthhub.workout;

import com.example.healthhub.exercise.Exercise;
import com.example.healthhub.exercise.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/workout")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private ExerciseService exerciseService;

    // Obtener una rutina por ID
    @GetMapping("/{id}")
    public Optional<Workout> getWorkoutById(@PathVariable("id") Long id) {
        return workoutService.getWorkoutById(id);
    }

    // Guardar una nueva rutina
    @PostMapping("/save")
    public Workout saveWorkout(@RequestBody Workout workout) {
        return workoutService.saveWorkout(workout);
    }

    // Agregar un ejercicio a una rutina
    @PostMapping("/{workoutId}/add-exercise")
    public Workout addExerciseToWorkout(@PathVariable Long workoutId, @RequestBody Long exerciseId) {
        Optional<Workout> workout = workoutService.getWorkoutById(workoutId);
        Optional<Exercise> exercise = exerciseService.getExerciseById(exerciseId);

        if (workout.isPresent() && exercise.isPresent()) {
            Workout existingWorkout = workout.get();
            Exercise existingExercise = exercise.get();

            existingExercise.setWorkout(existingWorkout);
            existingWorkout.getExercises().add(existingExercise);

            workoutService.saveWorkout(existingWorkout);

            return existingWorkout;
        } else {
            throw new RuntimeException("Workout or Exercise not found");
        }
    }
}
