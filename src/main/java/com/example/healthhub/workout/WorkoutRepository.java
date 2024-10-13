package com.example.healthhub.workout;

import com.example.healthhub.client.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkoutRepository extends CrudRepository<Workout, Long> {
    Iterable<Workout> findByClient(Client client);
    Optional<Workout> findByIdAndClient(Long id, Client client);
}