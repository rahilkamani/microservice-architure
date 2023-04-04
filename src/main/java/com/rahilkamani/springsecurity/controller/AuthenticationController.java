package com.rahilkamani.springsecurity.controller;

import com.rahilkamani.springsecurity.beans.AuthenticationRequest;
import com.rahilkamani.springsecurity.beans.AuthenticationResponse;
import com.rahilkamani.springsecurity.beans.RegsiterRequest;
import com.rahilkamani.springsecurity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegsiterRequest request){
        return ResponseEntity.ok(service.regsiter(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){

        return  ResponseEntity.ok(service.authenticate(request));
    }

}
