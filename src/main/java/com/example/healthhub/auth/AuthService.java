package com.example.healthhub.auth;

import com.example.healthhub.client.Role;
import com.example.healthhub.client.Client;
import com.example.healthhub.client.ClientRepository;
import com.example.healthhub.jwt.JwtService;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ClientRepository clientRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = clientRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Client not found"));
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        Client client = Client.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
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

