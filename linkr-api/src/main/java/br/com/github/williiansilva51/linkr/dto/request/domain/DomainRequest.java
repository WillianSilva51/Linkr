package br.com.github.williiansilva51.linkr.dto.request.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DomainRequest(
        @Schema(description = "Domain URL", example = "example.com")
        @NotBlank(message = "Domain URL cannot be blank")
        @Size(min = 5, max = 50, message = "Domain URL must be between 5 and 50 characters")
        String domainUrl) {
}
