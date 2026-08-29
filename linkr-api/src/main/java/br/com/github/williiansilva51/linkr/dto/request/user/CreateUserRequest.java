package br.com.github.williiansilva51.linkr.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CreateUserRequest {
    @Schema(description = "UserEntity name", example = "John Mendes")
    @NotBlank(message = "Name cannot be blank")
    @Length(min = 10, max = 100)
    private String name;

    @Schema(description = "UserEntity email", example = "John@gmail.com")
    @NotBlank(message = "Email cannot be blank")
    @Length(min = 10, max = 100, message = "Email must be between 10 and 100 characters")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "UserEntity password", example = "12345678")
    @NotBlank(message = "Password cannot be blank")
    @Length(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;
}
