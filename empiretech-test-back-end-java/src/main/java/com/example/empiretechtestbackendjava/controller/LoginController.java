package com.example.empiretechtestbackendjava.controller;

import com.example.empiretechtestbackendjava.dto.UserLogin;
import com.example.empiretechtestbackendjava.service.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid UserLogin userLogin) {
        var authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
        var token = jwtService.generateToken(authentication.getName());

        return ResponseEntity.ok(token);
    }
}
