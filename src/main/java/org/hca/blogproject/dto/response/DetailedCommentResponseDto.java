package org.hca.blogproject.dto.response;

import lombok.Builder;

@Builder
public record DetailedCommentResponseDto(Long id, String commenterName, PostResponseDto post, String commentContent) {
}
