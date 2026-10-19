package br.com.github.williiansilva51.linkr.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "UserEntity creation request")
public record CreateUserRequest(
        @Schema(description = "UserEntity name", example = "John Mendes")
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 5, max = 40, message = "Name must be between 5 and 40 characters")
        String name,

        @Schema(description = "UserEntity email", example = "John@gmail.com")
        @NotBlank(message = "Email cannot be blank")
        @Size(min = 10, max = 70, message = "Email must be between 10 and 70 characters")
        @Email(message = "Invalid email format")
        String email,

        @Schema(description = "UserEntity password", example = "12345678")
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 70, message = "Password must be between 8 and 70 characters")
        String password
) {
}