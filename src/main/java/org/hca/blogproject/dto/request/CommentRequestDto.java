package org.hca.blogproject.dto.request;

public record CommentRequestDto(Long userId, Long postId, String content) {
}
