package com.example.empiretechtestbackendjava.controller;

import com.example.empiretechtestbackendjava.domain.dtos.UserLogin;
import com.example.empiretechtestbackendjava.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Login", description = "Endpoints for Login")
public class LoginController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    @Operation(summary = "Login to the system", description = "Authenticate user and return a token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in"),
            @ApiResponse(responseCode = "400", description = "Invalid login credentials"),
    })
    @PostMapping
    public ResponseEntity<String> login(
            @Parameter(description = "User credentials for login", required = true)
            @RequestBody @Valid UserLogin userLogin
    ) {
        var authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
        var token = jwtService.generateToken(authentication.getName());

        return ResponseEntity.ok(token);
    }
}
