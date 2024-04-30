package org.hca.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.PostRequestDto;
import org.hca.blogproject.dto.response.PostResponseDto;
import org.hca.blogproject.entity.Post;
import org.hca.blogproject.entity.User;
import org.hca.blogproject.mapper.CustomPostMapper;
import org.hca.blogproject.mapper.PostMapper;
import org.hca.blogproject.mapper.UserMapper;
import org.hca.blogproject.repository.PostRepository;
import org.hca.blogproject.service.rules.PostBusinessRules;
import org.hca.blogproject.service.rules.UserBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CustomPostMapper customPostMapper;
    private final UserBusinessRules userBusinessRules;
    private final PostBusinessRules postBusinessRules;


    public PostResponseDto saveDto(PostRequestDto dto) {
        userBusinessRules.checkIfUserExistsById(dto.userId());

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
}