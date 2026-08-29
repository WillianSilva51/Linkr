package br.com.github.williiansilva51.linkr.service;

import br.com.github.williiansilva51.linkr.database.model.UserEntity;
import br.com.github.williiansilva51.linkr.database.repository.UserRepository;
import br.com.github.williiansilva51.linkr.dto.request.user.CreateUserRequest;
import br.com.github.williiansilva51.linkr.dto.response.user.UserResponse;
import br.com.github.williiansilva51.linkr.handler.exceptions.UserAlreadyExistsException;
import br.com.github.williiansilva51.linkr.handler.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        UserEntity userEntity = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(userEntity);

        return new UserResponse(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }

    public UserResponse findUserById(Integer id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return new UserResponse(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }


}
