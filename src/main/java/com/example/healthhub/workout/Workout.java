package com.example.healthhub.workout;

import com.example.healthhub.exercise.Exercise;
import com.example.healthhub.client.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Exercise> exercises = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;

    public Workout() {}

    public Workout(String name, String description, List<Exercise> exercises, Client client) {
        this.name = name;
        this.description = description;
        this.exercises = exercises;
        this.client = client;
    }

    public void addExercise(Exercise exercise) {
        if (exercises.contains(exercise)) {
            throw new RuntimeException("The exercise is already in the workout");
        }
        exercises.add(exercise);
        exercise.setWorkout(this);
    }

    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
        exercise.setWorkout(null);
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}