package com.example.healthhub.exercise;

public class ExerciseApiResponse {
    private String name;
    private String muscle;
    private String instructions;

    // Getters y Setters
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

    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
