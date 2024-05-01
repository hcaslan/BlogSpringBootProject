package org.hca.blogproject.dto.response;

import lombok.Builder;

@Builder
public record CommentResponseDto(Long id, String commenterName, String commentContent) {
}
