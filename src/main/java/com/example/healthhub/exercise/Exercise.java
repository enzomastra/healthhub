package com.example.healthhub.exercise;

import com.example.healthhub.workout.Workout;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    // Puedes cambiar "type" a muscle si prefieres
    @NotBlank(message = "Muscle is required")
    private String muscle;

    @NotNull(message = "Repetitions must not be null")
    @Min(value = 1, message = "Repetitions must be at least 1")
    private Integer repetitions;

    @NotNull(message = "Sets must not be null")
    @Min(value = 1, message = "Sets must be at least 1")
    private Integer sets;

    @Column(length = 1000) // Aumenta el límite si esperas textos largos
    private String instructions;

    @ManyToOne
    private Workout workout;

    public Exercise() {}

    public Exercise(String name, String muscle, Integer repetitions, Integer sets, String instructions) {
        this.name = name;
        this.muscle = muscle;
        this.repetitions = repetitions;
        this.sets = sets;
        this.instructions = instructions;
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

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }
}
