package org.hca.blogproject.dto.response;

public record UserResponseDto(Long id, String firstname, String lastname, String email, String password) {
}
