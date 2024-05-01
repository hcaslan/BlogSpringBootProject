package org.hca.blogproject.dto.response;


import lombok.Builder;

import java.util.List;

@Builder
public record DetailedPostResponseDto(Long id, String title, String content, String postWriterName, List<String> categories, List<String> likes, List<CommentResponseDto> commentList) {
}
