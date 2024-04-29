package org.hca.blogproject.dto;


import lombok.Builder;
import org.hca.blogproject.entity.Category;

import java.util.List;

@Builder
public record PostIntermediateDto(
        String title,
        String content,
        Long userId,
        List<Category> categories) {
}
