package br.com.github.williiansilva51.linkr.service;

import br.com.github.williiansilva51.linkr.database.model.RolesEntity;
import br.com.github.williiansilva51.linkr.database.model.UserEntity;
import br.com.github.williiansilva51.linkr.database.repository.RolesRepository;
import br.com.github.williiansilva51.linkr.database.repository.UserRepository;
import br.com.github.williiansilva51.linkr.dto.request.user.CreateUserRequest;
import br.com.github.williiansilva51.linkr.dto.response.user.UserResponse;
import br.com.github.williiansilva51.linkr.enums.RolesType;
import br.com.github.williiansilva51.linkr.handler.exceptions.RoleNotFoundException;
import br.com.github.williiansilva51.linkr.handler.exceptions.UserAlreadyExistsException;
import br.com.github.williiansilva51.linkr.handler.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        RolesEntity roles = rolesRepository.findByName(RolesType.USER.name())
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        UserEntity userEntity = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(roles))
                .build();

        userRepository.save(userEntity);

        return new UserResponse(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }

    public UserResponse findUserById(Integer id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return new UserResponse(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }

    public UserResponse findUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return new UserResponse(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }
}
