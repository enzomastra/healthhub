package com.example.healthhub.exercise;

import com.example.healthhub.workout.Workout;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    private Integer repetitions;
    private Integer sets;

    @ManyToOne
    private Workout workout;

    

    public Exercise() {}

    public Exercise(String name, String type, Integer repetitions, Integer sets) {
        this.name = name;
        this.type = type;
        this.repetitions = repetitions;
        this.sets = sets;
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


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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

    
    public Workout getWorkout() {
        return workout;
    }
    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

}
