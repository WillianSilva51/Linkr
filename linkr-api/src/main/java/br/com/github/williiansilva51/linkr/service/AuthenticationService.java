package br.com.github.williiansilva51.linkr.service;

import br.com.github.williiansilva51.linkr.config.TokenProvider;
import br.com.github.williiansilva51.linkr.dto.request.auth.AuthRequest;
import br.com.github.williiansilva51.linkr.dto.request.user.CreateUserRequest;
import br.com.github.williiansilva51.linkr.dto.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public UserResponse register(CreateUserRequest request) {
        return userService.createUser(request);
    }

    public String login(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            return tokenProvider.generateToken(authentication);
        } catch (BadCredentialsException e) {
            log.debug("Invalid credentials: {}", e.getMessage());
            throw new BadCredentialsException("Invalid credentials");
        } catch (Exception e) {
            log.error("Authentication error", e);
            throw new RuntimeException(e);
        }
    }
}
