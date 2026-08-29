package br.com.github.williiansilva51.linkr.service;

import br.com.github.williiansilva51.linkr.database.model.UserEntity;
import br.com.github.williiansilva51.linkr.database.repository.UserRepository;
import br.com.github.williiansilva51.linkr.dto.request.user.CreateUserRequest;
import br.com.github.williiansilva51.linkr.dto.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists"); // TODO: Customize exception
        }

        // TODO: Encrypt password

        UserEntity userEntity = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        userRepository.save(userEntity);

        return new UserResponse(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }

    public UserResponse findUserById(Integer id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserEntity not found")); // TODO: Customize exception

        return new UserResponse(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }


}
