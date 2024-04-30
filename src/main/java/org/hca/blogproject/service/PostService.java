package org.hca.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.PostRequestDto;
import org.hca.blogproject.dto.response.PostResponseDto;
import org.hca.blogproject.entity.Category;
import org.hca.blogproject.entity.Post;
import org.hca.blogproject.mapper.CustomPostMapper;
import org.hca.blogproject.repository.PostRepository;
import org.hca.blogproject.service.rules.CategoryBusinessRules;
import org.hca.blogproject.service.rules.PostBusinessRules;
import org.hca.blogproject.service.rules.UserBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CustomPostMapper customPostMapper;
    private final UserBusinessRules userBusinessRules;
    private final PostBusinessRules postBusinessRules;
    private final CategoryBusinessRules categoryBusinessRules;


    public PostResponseDto saveDto(PostRequestDto dto) {
        userBusinessRules.checkIfUserExistsById(dto.userId());
        userBusinessRules.checkIfUserDeleted(dto.userId());

        Post post = customPostMapper.postRequestDtoToPost(dto);
        postRepository.save(post);
        return customPostMapper.postToPostResponseDto(post);
    }


    public List<PostResponseDto> findAllDto() {
        return  postRepository.findAll()
                .stream()
                .filter(post ->!post.isDeleted())
                .map(customPostMapper::postToPostResponseDto)
                .collect(Collectors.toList());
    }

    public PostResponseDto findDtoById(Long id) {
        postBusinessRules.checkIfPostExistsById(id);
        postBusinessRules.checkIfPostDeleted(id);

        return customPostMapper.postToPostResponseDto(postRepository.findById(id).get());//checked at business rules
    }

    public PostResponseDto updateDto(Long id, PostRequestDto request) {
        postBusinessRules.checkIfPostExistsById(id);
        postBusinessRules.checkIfPostDeleted(id);

        Post postToUpdate = customPostMapper.postRequestDtoToPost(request);
        postToUpdate.setId(id);
        postRepository.save(postToUpdate);
        return customPostMapper.postToPostResponseDto(postToUpdate);
    }

    public PostResponseDto deleteDto(Long id) {
        postBusinessRules.checkIfPostExistsById(id);

        Post postToDelete = postRepository.findById(id).get();//checked at business rules
        List<Category> emptyList = new ArrayList<>();
        postToDelete.setCategories(emptyList);
        postRepository.delete(postToDelete);
        return customPostMapper.postToPostResponseDto(postToDelete);
    }

    public PostResponseDto setToDeletedDto(Long id) {
        postBusinessRules.checkIfPostExistsById(id);
        postBusinessRules.checkIfPostDeleted(id);

        Post postToDelete = postRepository.findById(id).get();//checked at business rules
        postToDelete.setDeleted(true);
        postToDelete.setDeletedAt(LocalDateTime.now().toString());
        postRepository.save(postToDelete);
        return customPostMapper.postToPostResponseDto(postToDelete);
    }

    public List<PostResponseDto> findByUserId(Long id) {
        userBusinessRules.checkIfUserExistsById(id);
        List<Post> postList = postRepository.findByUserId(id);
        postBusinessRules.checkIfPostListEmpty(postList);

        return customPostMapper.postListToPostResponseDtoList(postList);
    }

    public List<PostResponseDto> findByCategoryId(Long id) {
        categoryBusinessRules.checkIfCategoryExistsById(id);
        List<Post> postList = postRepository.findByCategoryId(id);
        postBusinessRules.checkIfPostListEmpty(postList);

        return customPostMapper.postListToPostResponseDtoList(postList);
    }

    public List<PostResponseDto> search(String search_key) {
        List<Post> postList = postRepository.search(search_key);
        postBusinessRules.checkIfPostListEmpty(postList);

        return customPostMapper.postListToPostResponseDtoList(postList);
    }

    public List<PostResponseDto> findByCategoryName(String name) {
        categoryBusinessRules.checkIfCategoryExistsByName(name);
        List<Post> postList = postRepository.findByCategoryName(name);
        postBusinessRules.checkIfPostListEmpty(postList);

        return customPostMapper.postListToPostResponseDtoList(postList);
    }


}