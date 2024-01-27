package com.parker.jwtTest.security.service;

import com.parker.jwtTest.model.Role;
import com.parker.jwtTest.model.User;
import com.parker.jwtTest.repository.UserRepository;
import com.parker.jwtTest.security.AuthenticateRequest;
import com.parker.jwtTest.security.AuthenticationResponse;
import com.parker.jwtTest.security.RegisterRequest;
import com.parker.jwtTest.security.config.JwtService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        User user=User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String jwt=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user=userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwt=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }
}
