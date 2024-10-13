package com.example.healthhub.exercise;

import com.example.healthhub.workout.Workout;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "workout_id") // Este campo representará la relación con la rutina
    private Long workoutId; // Almacena solo el ID de la rutina

    @ManyToOne
    @JoinColumn(name = "workout_id", insertable = false, updatable = false)
    @JsonBackReference
    private Workout workout; // Relación opcional para acceder a Workout si es necesario

    public Exercise() {}

    public Exercise(String name, Long workoutId) {
        this.name = name;
        this.workoutId = workoutId;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }
}
