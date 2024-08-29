package com.example.healthhub.auth;

import com.example.healthhub.client.Client;
import com.example.healthhub.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClientRepository clientRepository;

    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {
        Client client = Client.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .weight(request.getWeight())
                .height(request.getHeight())
                .build();

        clientRepository.save(client);

        return AuthResponse.builder()
                .token(null)
                .build();
    }
}

