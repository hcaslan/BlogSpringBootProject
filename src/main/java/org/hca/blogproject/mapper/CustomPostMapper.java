package org.hca.blogproject.mapper;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.PostRequestDto;
import org.hca.blogproject.dto.response.CommentResponseDto;
import org.hca.blogproject.dto.response.DetailedPostResponseDto;
import org.hca.blogproject.dto.response.PostResponseDto;
import org.hca.blogproject.entity.Category;
import org.hca.blogproject.entity.Comment;
import org.hca.blogproject.entity.Post;
import org.hca.blogproject.entity.User;
import org.hca.blogproject.service.CategoryService;
import org.hca.blogproject.service.UserService;
import org.hca.blogproject.service.rules.CategoryBusinessRules;
import org.hca.blogproject.service.rules.UserBusinessRules;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @CustomMapper
 */
@RequiredArgsConstructor
@Component
public class CustomPostMapper {
    private final UserBusinessRules userBusinessRules;
    private final CategoryBusinessRules categoryBusinessRules;
    private final CategoryService categoryService;
    private final UserService userService;
    private final String DELETED_USER = "DELETED_USER";

    public List<PostResponseDto> postListToPostResponseDtoList(List<Post> postList) {
        List<PostResponseDto> results = new ArrayList<>();

        postList.forEach(post -> {
            results.add(postToPostResponseDto(post));
        });
        return results;
    }
    public DetailedPostResponseDto postToDetailedPostResponseDto(Post post) {
        List<String> categoryNames = getStringCategoryNames(post);
        List<String> likes = getStringLikes(post);
        List<CommentResponseDto> commentResponseDtoList = getCommentResponseDtoList(post);
            return DetailedPostResponseDto.builder()
                    .id(post.getId())
                    .content(post.getContent())
                    .title(post.getTitle())
                    .categories(categoryNames)
                    .postWriterName(post.getUser().isDeleted() ? DELETED_USER : getUserFirstAndLastName(post.getUser()))
                    .likes(likes)
                    .commentList(commentResponseDtoList)
                    .createdAt(post.getCreatedAt())
                    .build();
    }


    public Post postRequestDtoToPost(PostRequestDto dto) {
        userBusinessRules.checkIfExistsById(dto.userId());
        List<Category> categoryList = dto.categories().stream()
                .flatMap(categoryName -> {
                    List<Category> categories = new ArrayList<>();
                    if (!categoryBusinessRules.isCategoryExistsByName(categoryName)) {
                        categories.add(categoryService.save(Category.builder().name(categoryName).build()));
                    } else {
                        categories.add(categoryService.findCategoryByNameReturnCategory(categoryName));
                    }
                    return categories.stream();
                })
                .collect(Collectors.toList());

        return Post.builder()
                .title(dto.title())
                .content(dto.content())
                .user(userService.findById(dto.userId()).get())//checked at business rules
                .categories(categoryList)
                .build();
    }
    public PostResponseDto postToPostResponseDto(Post post) {
        List<String> categoryNames = getStringCategoryNames(post);
            return PostResponseDto.builder()
                    .id(post.getId())
                    .content(post.getContent())
                    .title(post.getTitle())
                    .categories(categoryNames)
                    .postWriterName(post.getUser().isDeleted() ? DELETED_USER : getUserFirstAndLastName(post.getUser()))
                    .build();

    }

    private List<String> getStringLikes(Post post) {
        return post.getLikes().stream()
                .filter(user -> !user.isDeleted())
                .map(this::getUserFirstAndLastName)
                .collect(Collectors.toList());
    }

    private List<String> getStringCategoryNames(Post post) {
        return post.getCategories().stream()
                .filter(category ->!category.isDeleted())
                .map(Category::getName)
                .collect(Collectors.toList());
    }
    private List<CommentResponseDto> getCommentResponseDtoList(Post post) {
        return post.getComments().stream()
                .map(this::commentToCommentResponseDto)
                .collect(Collectors.toList());

    }
    private CommentResponseDto commentToCommentResponseDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .commentContent(comment.getContent())
                .commenterName(getUserFirstAndLastName(comment))
                .build();
    }

    private String getUserFirstAndLastName(User post) {
        return post.getFirstname() + " " + post.getLastname();
    }
    private String getUserFirstAndLastName(Comment comment) {
        return comment.getUser().getFirstname() + " " + comment.getUser().getLastname();
    }
}
