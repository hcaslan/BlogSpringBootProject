package org.hca.blogproject.mapper;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.PostRequestDto;
import org.hca.blogproject.dto.response.PostResponseDto;
import org.hca.blogproject.entity.Category;
import org.hca.blogproject.entity.Post;
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
    private final CategoryBusinessRules categoryBusinessRules;
    private final CategoryService categoryService;
    private final UserService userService;
    private final UserBusinessRules userBusinessRules;

    public PostResponseDto postToPostResponseDto(Post post) {
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

    public Post postRequestDtoToPost(PostRequestDto dto) {
        userBusinessRules.checkIfUserExistsById(dto.userId());
        List<Category> categoryList = dto.categories().stream()
                .flatMap(categoryName -> {
                    List<Category> categories = new ArrayList<>();
                    if (!categoryBusinessRules.isCategoryExistsByName(categoryName)) {
                        categories.add(categoryService.save(Category.builder().name(categoryName).build()));
                    } else {
                        categories.add(categoryService.findCategoryByName(categoryName));
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
    public List<PostResponseDto> postListToPostResponseDtoList(List<Post> postList) {
        List<PostResponseDto> results = new ArrayList<>();

        postList.forEach(post -> {
            results.add(postToPostResponseDto(post));
        });
        return results;
    }
}
