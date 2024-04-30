package org.hca.blogproject.dto.response;


import lombok.Builder;

import java.util.List;
@Builder
public record PostResponseDto(Long id, String title, String content, String userFirstAndLastName, List<String> categories) {
}
