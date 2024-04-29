package org.hca.blogproject.dto.response;


import lombok.Builder;

import java.util.List;

public record PostResponseDto(String title, String content, Long userId, List<String> categories) {
}
