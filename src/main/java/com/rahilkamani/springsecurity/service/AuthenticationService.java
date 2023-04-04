package com.rahilkamani.springsecurity.service;

import com.rahilkamani.springsecurity.beans.AuthenticationRequest;
import com.rahilkamani.springsecurity.beans.AuthenticationResponse;
import com.rahilkamani.springsecurity.beans.RegsiterRequest;
import com.rahilkamani.springsecurity.config.ApplicationConfig;
import com.rahilkamani.springsecurity.config.JwtService;
import com.rahilkamani.springsecurity.repository.UserRepository;
import com.rahilkamani.springsecurity.user.Role;
import com.rahilkamani.springsecurity.user.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final ApplicationConfig config;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse regsiter(RegsiterRequest request) {

    var user = Users.builder().firstName(request.getFirstName()).lastName(request.getLastname())
            .userName(request.getUserName()).user_password(config.passwordEncoder().encode(request.getPassword()))
            .user_role(Role.USER).build();
    userRepository.save(user);
    var jwtToken = jwtService.generateJwtToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(),request.getPassword()));
        var user = userRepository.findByuserName(request.getUserName()).orElseThrow();
        var jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }
}
