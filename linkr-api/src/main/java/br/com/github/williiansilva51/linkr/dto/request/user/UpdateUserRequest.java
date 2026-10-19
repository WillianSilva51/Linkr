package br.com.github.williiansilva51.linkr.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

@Schema(description = "UserEntity update request")
public record UpdateUserRequest(
        @Schema(description = "UserEntity name", example = "John Mendes")
        @Length(min = 10, max = 40, message = "Name must be between 0 and 40 characters")
        String name,

        @Schema(description = "UserEntity email", example = "John@gmail.com")
        @Length(min = 10, max = 70, message = "Email must be between 10 and 70 characters")
        @Email(message = "Invalid email format")
        String email,

        @Schema(description = "UserEntity password", example = "12345678")
        @Length(min = 8, max = 70, message = "Password must be between 8 and 70 characters")
        String password
) {
}
