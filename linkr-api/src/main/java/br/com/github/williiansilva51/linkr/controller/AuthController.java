package br.com.github.williiansilva51.linkr.controller;

import br.com.github.williiansilva51.linkr.dto.request.auth.AuthRequest;
import br.com.github.williiansilva51.linkr.dto.request.user.CreateUserRequest;
import br.com.github.williiansilva51.linkr.dto.response.token.TokenResponse;
import br.com.github.williiansilva51.linkr.dto.response.user.UserResponse;
import br.com.github.williiansilva51.linkr.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration.")
@Validated
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account.")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody CreateUserRequest request) {
        UserResponse userResponse = authenticationService.register(request);

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user", description = "Generates a token for authenticated users.")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody AuthRequest request) {
        TokenResponse tokenResponse = authenticationService.login(request);

        return ResponseEntity.ok(tokenResponse);
    }
}
