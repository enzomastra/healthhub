package com.example.healthhub.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    public Exercise saveExercise(@RequestBody Exercise exercise) {
        return exerciseService.saveExercise(exercise);
    }

    @DeleteMapping("/{id}")
    public String deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return "Exercise with Id " + id + " has been deleted";
    }

    @PutMapping("/{id}")
    public Exercise updateExercise(@RequestBody Exercise exercise, @PathVariable Long id) {
        return exerciseService.updateExercise(exercise, id);
    }

    // Endpoint para buscar ejercicios en la API externa y guardarlos
    @GetMapping("/search")
    public List<Exercise> searchExercises(@RequestParam String query) {
        return exerciseService.searchExercisesFromApi(query);
    }
}
