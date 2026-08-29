package br.com.github.williiansilva51.linkr.controller;

import br.com.github.williiansilva51.linkr.dto.request.auth.AuthRequest;
import br.com.github.williiansilva51.linkr.dto.request.user.CreateUserRequest;
import br.com.github.williiansilva51.linkr.dto.response.token.TokenResponse;
import br.com.github.williiansilva51.linkr.dto.response.user.UserResponse;
import br.com.github.williiansilva51.linkr.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody CreateUserRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody AuthRequest request) {
        return authenticationService.login(request);
    }
}
