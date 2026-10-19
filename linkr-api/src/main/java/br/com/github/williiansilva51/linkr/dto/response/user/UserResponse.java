package br.com.github.williiansilva51.linkr.dto.response.user;

import java.util.UUID;

public record UserResponse(UUID id, String name, String email) {
}
