package com.example.healthhub.auth;

import com.example.healthhub.client.Role;
import com.example.healthhub.client.Client;
import com.example.healthhub.client.ClientRepository;
import com.example.healthhub.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ClientRepository clientRepository;
    private final JwtService jwtService;

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
                .role(Role.USER)
                .build();

        clientRepository.save(client);

        return AuthResponse.builder()
                .token(jwtService.getToken(client))
                .build();
    }
}

