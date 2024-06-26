package org.hca.blogproject.mapper.customMapper;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.constant.Constant;
import org.hca.blogproject.dto.response.*;
import org.hca.blogproject.entity.Category;
import org.hca.blogproject.entity.Post;
import org.hca.blogproject.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @CustomMapper
 * Contains custom mapping logic for specific scenarios.
 */
@RequiredArgsConstructor
@Component
public class CustomUserMapper {
    public DetailedUserResponseDto userToDetailedUserResponseDto(User user) {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        user.getPosts().forEach(post -> {
            postResponseDtoList.add(this.postToPostResponseDto(post));
        });
        return DetailedUserResponseDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .posts(postResponseDtoList)
                .password(user.getPassword())
                .build();
    }
    public PostResponseDto postToPostResponseDto(Post post) {
        List<String> categoryNames = getStringCategoryNames(post);
            return PostResponseDto.builder()
                    .id(post.getId())
                    .content(post.getContent())
                    .title(post.getTitle())
                    .categories(categoryNames)
                    .postWriterName(post.getUser().isDeleted() ? Constant.DELETED_USER : getUserFirstAndLastName(post.getUser()))
                    .build();
    }
    private List<String> getStringCategoryNames(Post post) {
        return post.getCategories().stream()
                .filter(category ->!category.isDeleted())
                .map(Category::getName)
                .collect(Collectors.toList());
    }
    private String getUserFirstAndLastName(User user) {
        return user.getFirstname() + " " + user.getLastname();
    }
}
