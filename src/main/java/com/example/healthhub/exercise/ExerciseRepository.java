package com.example.healthhub.exercise;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long>{
    Optional<Exercise> findByName(String name);
    
}
