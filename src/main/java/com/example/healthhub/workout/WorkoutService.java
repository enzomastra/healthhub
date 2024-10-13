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

    // Guardar una rutina en la base de datos
    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    // Buscar una rutina por ID
    public Optional<Workout> getWorkoutById(Long id) {
        return workoutRepository.findById(id);
    }

    // Obtener todas las rutinas de un cliente
    public Iterable<Workout> getWorkoutsByClient(Client client) {
        return workoutRepository.findByClient(client);
    }

    // Buscar una rutina por ID y cliente
    public Optional<Workout> getWorkoutByIdAndClient(Long id, Client client) {
        return workoutRepository.findByIdAndClient(id, client);
    }

    // Obtener todas las rutinas
    public Iterable<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    // añadir un ejercicio a una rutina
    public Workout addExerciseToWorkout(Long workoutId, String exerciseName) {
        // Buscar la rutina por ID
        Optional<Workout> workoutOptional = workoutRepository.findById(workoutId);
        Workout workout = workoutOptional.orElseThrow(() -> new RuntimeException("Workout not found"));

        // Buscar el ejercicio por nombre
        Optional<Exercise> exerciseOptional = exerciseRepository.findByName(exerciseName);
        if (exerciseOptional.isPresent()) {
            Exercise exercise = exerciseOptional.get();
            // Verificar si el ejercicio ya está asociado a la rutina
            if (exercise.getWorkoutId() != null && exercise.getWorkoutId().equals(workoutId)) {
                throw new RuntimeException("El ejercicio ya está añadido a esta rutina.");
            }
        }

        // Si el ejercicio no está asociado, llamar al método de ExerciseService para asociarlo
        exerciseService.addExerciseToWorkout(exerciseName, workoutId);

        // Recargar la rutina para obtener la lista actualizada de ejercicios
        workout = workoutRepository.findById(workoutId).orElseThrow(() -> new RuntimeException("Workout not found"));
        return workout;
    }

    // Eliminar una rutina
    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }
}