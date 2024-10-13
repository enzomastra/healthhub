package com.example.healthhub.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/getall")
    public List<Exercise> getExercises() {
        return exerciseService.getExercises();
    }

    @GetMapping("/{id}")
    public Optional<Exercise> getExerciseById(@PathVariable Long id) {
        return exerciseService.getExerciseById(id);
    }

    @PostMapping("/add")
    public void addExercise(@RequestBody Exercise exercise) {
        exerciseService.addExerciseToWorkout(exercise.getName(), exercise.getWorkoutId());
    }

    @DeleteMapping("/{id}")
    public String deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return "Exercise with Id " + id + " has been deleted";
    }

    @PutMapping("/{id}")
    public String updateExercise(@RequestBody Exercise exercise, @PathVariable Long id) {
        Exercise updatedExercise = exerciseService.updateExercise(exercise, id);
        if (updatedExercise == null) {
            return "Exercise not found"; // Mensaje simple si no se encuentra el ejercicio
        }
        return "Exercise updated successfully"; // Mensaje de éxito
    }


    // Endpoint para buscar ejercicios en la API externa y guardarlos
    @GetMapping("/search")
    public ResponseEntity<List<ExerciseDTO>> searchExercises(@RequestParam String query) {
        List<ExerciseDTO> exercises = exerciseService.searchExercisesFromApi(query);
        return ResponseEntity.ok(exercises); // Devolver ExerciseDTO
    }
}
