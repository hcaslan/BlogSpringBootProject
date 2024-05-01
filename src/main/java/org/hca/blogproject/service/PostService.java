package org.hca.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.PostRequestDto;
import org.hca.blogproject.dto.response.DetailedPostResponseDto;
import org.hca.blogproject.dto.response.PostResponseDto;
import org.hca.blogproject.entity.Comment;
import org.hca.blogproject.entity.Post;
import org.hca.blogproject.mapper.CustomPostMapper;
import org.hca.blogproject.repository.CommentRepository;
import org.hca.blogproject.repository.PostRepository;
import org.hca.blogproject.service.rules.CategoryBusinessRules;
import org.hca.blogproject.service.rules.PostBusinessRules;
import org.hca.blogproject.service.rules.UserBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CustomPostMapper customPostMapper;
    private final UserBusinessRules userBusinessRules;
    private final PostBusinessRules postBusinessRules;
    private final CategoryBusinessRules categoryBusinessRules;
    private final UserService userService;
    private final CustomPostMapper customMapper;
    private final CommentRepository commentRepository;


    public PostResponseDto saveDto(PostRequestDto dto) {
        userBusinessRules.checkIfUserExistsById(dto.userId());
        userBusinessRules.checkIfUserDeleted(dto.userId());

        Post post = customMapper.postRequestDtoToPost(dto);
        postRepository.save(post);
        return customMapper.postToPostResponseDto(post);
    }


    public List<PostResponseDto> findAllDto() {
        return  postRepository.findAll()
                .stream()
                .filter(post ->!post.isDeleted())
                .map(customMapper::postToPostResponseDto)
                .collect(Collectors.toList());
    }

    public DetailedPostResponseDto findDetailedDtoById(Long id) {
        postBusinessRules.checkIfPostExistsById(id);
        //postBusinessRules.checkIfPostDeleted(id);

        return customPostMapper.postToDetailedPostResponseDto(postRepository.findById(id).get());//checked at business rules
    }

    public PostResponseDto updateDto(Long id, PostRequestDto request) {
        postBusinessRules.checkIfPostExistsById(id);
        //postBusinessRules.checkIfPostDeleted(id);

        Post postToUpdate = customMapper.postRequestDtoToPost(request);
        postToUpdate.setId(id);
        postRepository.save(postToUpdate);
        return customMapper.postToPostResponseDto(postToUpdate);
    }

    public PostResponseDto deleteDto(Long id) {
        postBusinessRules.checkIfPostExistsById(id);

        Post postToDelete = postRepository.findById(id).get();//checked at business rules
        postToDelete.setCategories(new ArrayList<>());
        postToDelete.setLikes(new ArrayList<>());
        List<Comment> comments = postToDelete.getComments();
        postToDelete.setComments(new ArrayList<>());
        commentRepository.deleteAll(comments);
        postRepository.delete(postToDelete);
        return customMapper.postToPostResponseDto(postToDelete);
    }

    public PostResponseDto setToDeletedDto(Long id) {
        postBusinessRules.checkIfPostExistsById(id);
        //postBusinessRules.checkIfPostDeleted(id);

        Post postToDelete = postRepository.findById(id).get();//checked at business rules
        postToDelete.setDeleted(true);
        postToDelete.setDeletedAt(LocalDateTime.now().toString());
        postRepository.save(postToDelete);
        return customMapper.postToPostResponseDto(postToDelete);
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


    public DetailedPostResponseDto like(Long userId, Long postId) {
        userBusinessRules.checkIfUserExistsById(userId);
        userBusinessRules.checkIfUserDeleted(userId);
        postBusinessRules.checkIfPostExistsById(postId);
        //postBusinessRules.checkIfPostDeleted(postId);
        postBusinessRules.checkIfPostLikedByUser(userId, postId);
        Post post = postRepository.findById(postId).get();//checked at business rules
        post.getLikes().add(userService.findById(userId).get()); //checked at business rules
        postRepository.save(post);
        return customPostMapper.postToDetailedPostResponseDto(post);
    }

    public DetailedPostResponseDto unlike(Long userId, Long postId) {
        userBusinessRules.checkIfUserExistsById(userId);
        userBusinessRules.checkIfUserDeleted(userId);
        postBusinessRules.checkIfPostExistsById(postId);
        //postBusinessRules.checkIfPostDeleted(postId);
        postBusinessRules.checkIfPostAlreadyLikedByUser(userId, postId);
        Post post = postRepository.findById(postId).get();//checked at business rules
        post.setLikes(post.getLikes().stream().filter(user -> !user.getId().equals((userId))).collect(Collectors.toList()));
        postRepository.save(post);
        return customPostMapper.postToDetailedPostResponseDto(post);
    }

    public PostResponseDto findDtoById(Long id) {
        postBusinessRules.checkIfPostExistsById(id);
        //postBusinessRules.checkIfPostDeleted(id);

        return customMapper.postToPostResponseDto(postRepository.findById(id).get());//checked at business rules
    }

    public Post findById(Long id) {
        postBusinessRules.checkIfPostExistsById(id);
        //postBusinessRules.checkIfPostDeleted(id);
        return postRepository.findById(id).get();//checked at business rules
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public List<PostResponseDto> getPostsInChronologicalOrder() {
        return customPostMapper.postListToPostResponseDtoList(postRepository.findAllByOrderByCreatedAtDesc());
    }
}