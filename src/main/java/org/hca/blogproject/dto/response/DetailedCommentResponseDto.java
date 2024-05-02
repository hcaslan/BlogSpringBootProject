package org.hca.blogproject.dto.response;

import lombok.Builder;

@Builder
public record DetailedCommentResponseDto(Long id, String commenterName, String commentContent, String createdAt, PostResponseDto post) {
}
