package com.example.healthhub.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ExerciseService {
    private final RestTemplate restTemplate;
    private final String apiUrl = "https://api.api-ninjas.com/v1/exercises";
    private final String apiKey = "y1+5gc17uz0x2dy4oSqjuA==zWeBLlSiqDlwnaoP";

    @Autowired
    private ExerciseRepository exerciseRepository;

    public ExerciseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Guardar un ejercicio en la base de datos
    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    // Obtener todos los ejercicios
    public List<Exercise> getExercises() {
        Iterable<Exercise> iterable = exerciseRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    // Obtener un ejercicio por ID
    public Optional<Exercise> getExerciseById(Long id) {
        return exerciseRepository.findById(id);
    }

    // Eliminar un ejercicio por ID
    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }

    // Actualizar un ejercicio
    public Exercise updateExercise(Exercise exercise, Long id) {
        Optional<Exercise> existingExercise = exerciseRepository.findById(id);
        if (existingExercise.isPresent()) {
            Exercise updatedExercise = existingExercise.get();
            updatedExercise.setName(exercise.getName());
            updatedExercise.setMuscle(exercise.getMuscle());
            updatedExercise.setInstructions(exercise.getInstructions());
            return exerciseRepository.save(updatedExercise);
        } else {
            throw new RuntimeException("Exercise not found");
        }
    }

    public List<Exercise> searchExercisesFromApi(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("muscle", query)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        headers.set("accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ExerciseApiResponse[]> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, ExerciseApiResponse[].class);

        return Arrays.stream(response.getBody())
                .map(this::mapToExercise)
                .collect(Collectors.toList());
    }

    private Exercise mapToExercise(ExerciseApiResponse apiResponse) {
        Exercise exercise = new Exercise();
        exercise.setName(apiResponse.getName());
        exercise.setMuscle(apiResponse.getMuscle());
        exercise.setInstructions(apiResponse.getInstructions());
        return exercise;
    }
}