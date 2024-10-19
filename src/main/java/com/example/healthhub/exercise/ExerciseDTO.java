package com.example.healthhub.exercise;

public class ExerciseDTO {
    private String name;
    private String muscle;
    private String instructions;

    public ExerciseDTO(String name, String muscle, String instructions) {
        this.name = name;
        this.muscle = muscle;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public String getMuscle() {
        return muscle;
    }

    public String getInstructions() {
        return instructions;
    }
}
