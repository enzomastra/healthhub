package com.example.healthhub.workout;

import com.example.healthhub.exercise.Exercise;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Workout name is required")
    private String name;

    private String description;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises;

    public Workout() {}

    public Workout(String name, String description, List<Exercise> exercises) {
        this.name = name;
        this.description = description;
        this.exercises = exercises;
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

    public String getDescription() {
        return description;
    }

    public List<Exercise> getExercises() {
    return exercises;
    }

}

   
