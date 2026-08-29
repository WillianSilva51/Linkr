package br.com.github.williiansilva51.linkr.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    @Schema(description = "UserEntity email for auth", example = "John@gmail.com")
    @NotBlank(message = "Email cannot be blank")
    @Length(min = 10, max = 70, message = "Email must be between 10 and 70 characters")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "UserEntity password for auth", example = "12345678")
    @NotBlank(message = "Password cannot be blank")
    @Length(min = 8, max = 70, message = "Password must be between 8 and 70 characters")
    private String password;
}
