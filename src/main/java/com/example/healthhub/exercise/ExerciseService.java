package com.example.healthhub.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    public Optional<Exercise> getExerciseById(Long id) {
        return exerciseRepository.findById(id);
    }

    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public List<Exercise> getExercises() {
        Iterable<Exercise> iterable = exerciseRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public ExerciseDTO getExerciseFromApi(String exerciseName) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("name", exerciseName)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ExerciseApiResponse[]> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, ExerciseApiResponse[].class);

        if (response.getBody() != null && response.getBody().length > 0) {
            ExerciseApiResponse apiResponse = response.getBody()[0];
            return new ExerciseDTO(apiResponse.getName(), apiResponse.getMuscle(), apiResponse.getInstructions());
        } else {
            return null;
        }
    }

    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }

    public Exercise updateExercise(Exercise exercise, Long id) {
        Optional<Exercise> existingExerciseOpt = exerciseRepository.findById(id);
        if (existingExerciseOpt.isPresent()) {
            Exercise existingExercise = existingExerciseOpt.get();
            existingExercise.setName(exercise.getName());
            existingExercise.setWorkoutId(exercise.getWorkoutId());
            return exerciseRepository.save(existingExercise);
        } else {
            return null;
        }
    }

    public List<ExerciseDTO> searchExercisesFromApi(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("name", query)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        headers.set("accept", "application/json");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String jwtToken = ((UserDetails) authentication.getPrincipal()).getUsername();
            headers.set("Authorization", "Bearer " + jwtToken);
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ExerciseApiResponse[]> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, ExerciseApiResponse[].class);

        return Arrays.stream(response.getBody())
                .map(apiResponse -> new ExerciseDTO(apiResponse.getName(), apiResponse.getMuscle(), apiResponse.getInstructions()))
                .collect(Collectors.toList());
    }

    public void addExerciseToWorkout(String exerciseName, Long workoutId) {
        Exercise exercise = new Exercise(exerciseName, workoutId);
        exerciseRepository.save(exercise);
    }
}
