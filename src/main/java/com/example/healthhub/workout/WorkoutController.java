package com.example.healthhub.workout;

import com.example.healthhub.exercise.Exercise;
import com.example.healthhub.exercise.ExerciseService;
import com.example.healthhub.client.Client;
import com.example.healthhub.client.ClientService;
import com.example.healthhub.workout.Workout;
import com.example.healthhub.workout.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/workout")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/client/{id}")
    public Client getClientById(@PathVariable Long id) {
        Optional<Client> clientOptional = clientService.getClientById(id);
        return clientOptional.orElseThrow(() -> new RuntimeException("Client not found"));
    }


    @GetMapping("/{id}")
    public Optional<Workout> getWorkoutById(@PathVariable("id") Long id) {
        return workoutService.getWorkoutById(id);
    }

    @GetMapping("/all")
    public Iterable<Workout> getAllWorkouts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Client client = clientService.getClientByEmail(email).orElseThrow(() -> new RuntimeException("Client not found"));
        return workoutService.getWorkoutsByClient(client);
    }

    @PostMapping("/save")
    public Workout saveWorkout(@RequestBody Workout workout) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Client client = clientService.getClientByEmail(email).orElseThrow(() -> new RuntimeException("Client not found"));
        workout.setClient(client);
        return workoutService.saveWorkout(workout);
    }

    @PutMapping("/{id}")
    public Workout updateWorkout(@PathVariable Long id, @RequestBody Workout workoutDetails) {
        Workout workout = workoutService.getWorkoutById(id).orElseThrow(() -> new RuntimeException("Workout not found"));
        workout.setName(workoutDetails.getName());
        workout.setDescription(workoutDetails.getDescription());
        workout.setExercises(workoutDetails.getExercises());
        return workoutService.saveWorkout(workout);
    }

    @PostMapping("/{workoutId}/add/{exerciseName}")
    public Workout addExerciseToWorkout(@PathVariable Long workoutId, @PathVariable String exerciseName) {
        return workoutService.addExerciseToWorkout(workoutId, exerciseName);
    }

    @DeleteMapping("/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return "Workout with Id " + id + " has been deleted";
    }
}