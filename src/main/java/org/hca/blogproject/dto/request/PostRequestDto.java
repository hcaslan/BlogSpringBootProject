package org.hca.blogproject.dto.request;


import java.util.List;

public record PostRequestDto(
        String title,
        String content,
        Long userId,
        List<String> categories) {
}
