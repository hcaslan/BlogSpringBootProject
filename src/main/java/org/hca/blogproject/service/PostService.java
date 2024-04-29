package org.hca.blogproject.service;

import org.hca.blogproject.dto.PostIntermediateDto;
import org.hca.blogproject.dto.request.PostRequestDto;
import org.hca.blogproject.dto.response.PostResponseDto;
import org.hca.blogproject.entity.Category;
import org.hca.blogproject.entity.Post;
import org.hca.blogproject.mapper.PostMapper;
import org.hca.blogproject.repository.PostRepository;
import org.hca.blogproject.service.rules.CategoryBusinessRules;
import org.hca.blogproject.service.rules.UserBusinessRules;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserBusinessRules userBusinessRules;
    private final CategoryBusinessRules categoryBusinessRules;
    private final CategoryService categoryService;
    private final PostMapper postMapper;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserBusinessRules userBusinessRules, CategoryBusinessRules categoryBusinessRules, PostMapper postMapper, CategoryService categoryService, UserService userService) {
        this.postRepository = postRepository;
        this.userBusinessRules = userBusinessRules;
        this.categoryBusinessRules = categoryBusinessRules;
        this.postMapper = postMapper;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public PostResponseDto saveDto(PostRequestDto dto) {
        userBusinessRules.checkIfUserExistsById(dto.userId());
        List<Category> categoryList = dto.categories().stream()
                .flatMap(categoryName -> {
                    List<Category> categories = new ArrayList<>();
                    if (!categoryBusinessRules.isCategoryExistsByName(categoryName)) {
                        categories.add(categoryService.save(Category.builder().name(categoryName).build()));
                    } else {
                        categories.add(categoryService.findCategoryByName(categoryName));
                    }
                    return categories.stream(); // Flatten the list into a stream of categories
                })
                .collect(Collectors.toList());

        Post postToSave = Post.builder()
                .title(dto.title())
                .content(dto.content())
                .user(userService.findById(dto.userId()).get())//checked at business rules
                .categories(categoryList)
                .build();
        return PostMapper.mapToDto(postRepository.save(postToSave));
    }

    public List<PostResponseDto> findAllDto() {
        return null;
    }

    public PostResponseDto findDtoById(Long id) {
        return null;
    }

    public PostResponseDto updateDto(Long id, PostRequestDto request) {
        return null;
    }

    public PostResponseDto deleteDto(Long id) {
        return null;
    }

    public PostResponseDto setToDeletedDto(Long id) {
        return null;
    }
}