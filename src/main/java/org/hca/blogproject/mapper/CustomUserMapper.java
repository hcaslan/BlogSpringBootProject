package org.hca.blogproject.mapper;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.response.DetailedUserResponseDto;
import org.hca.blogproject.dto.response.PostResponseDto;
import org.hca.blogproject.entity.Category;
import org.hca.blogproject.entity.Post;
import org.hca.blogproject.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @CustomMapper
 */
@RequiredArgsConstructor
@Component
public class CustomUserMapper {
    //private final CustomPostMapper customPostMapper;
    public DetailedUserResponseDto userToDetailedUserResponseDto(User user) {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        user.getPosts().forEach(post -> {
            postResponseDtoList.add(postToPostResponseDto(post));
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
    /* APPLICATION FAILED TO START
     * Description:
     * The dependencies of some of the beans in the application context form a cycle:
     *  ┌─────┐
     *  |  customPostMapper
     *  ↑     ↓
     *  |  userService
     *  ↑     ↓
     *  |  customUserMapper
     *  └─────┘
     * Action:
     * Relying upon circular references is discouraged and,
     * they are prohibited by default.
     * Update your application to remove the dependency cycle between beans.
     * As a last resort, it may be possible to break the cycle automatically by setting spring.main.allow-circular-references to true.
     *
     * Action:
     * Despite circular references being allowed,
     * the dependency cycle between beans could not be broken.
     * Update your application to remove the dependency cycle.
     *
     * Solution:
     * To break the circle, I copied the CustomPostMapper member postToPostResponseDto method here.
     */
    private PostResponseDto postToPostResponseDto(Post post) {
        List<String> categoryNames = post.getCategories().stream()
                .filter(category ->!category.isDeleted())
                .map(Category::getName)
                .collect(Collectors.toList());

        if(post.getUser().isDeleted()){
            return PostResponseDto.builder()
                    .id(post.getId())
                    .content(post.getContent())
                    .title(post.getTitle())
                    .categories(categoryNames)
                    .userFirstAndLastName("DELETED_USER")
                    .build();
        }else{
            return PostResponseDto.builder()
                    .id(post.getId())
                    .content(post.getContent())
                    .title(post.getTitle())
                    .categories(categoryNames)
                    .userFirstAndLastName(post.getUser().getFirstname() + " " + post.getUser().getLastname())
                    .build();
        }
    }
}
