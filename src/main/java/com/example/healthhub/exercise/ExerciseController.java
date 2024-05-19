package com.example.healthhub.exercise;

import com.example.healthhub.exercise.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    @Autowired

    private ExerciseService exerciseService;

    @GetMapping("/getall")
    public ArrayList<Exercise> getExercises() {
        return this.exerciseService.getExercises();
    }

    @GetMapping(path = "/{id}")
    public Optional<Exercise> getExerciseById(@PathVariable("id") Long id) {
        return this.exerciseService.getExerciseById(id);
    }

    @PostMapping("/save")
    public Exercise saveExercise(@RequestBody Exercise exercise) {
        return this.exerciseService.saveExercise(exercise);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteExercise(@PathVariable("id") Long id) {
        boolean ok = this.exerciseService.deleteExercise(id);

        if(ok){
            return "Exercise with Id" + id + "has been deleted";
        } else {
            return "Error, Exercise with Id" + id + "has not been deleted";
        }
    }

    @PutMapping(path = "/{id}")
    public Exercise updateExercise(@RequestBody Exercise request, @PathVariable("id") Long id) {
        return this.exerciseService.updateExercise(request, id);
    }
    
}
