package org.hca.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.PostRequestDto;
import org.hca.blogproject.dto.response.DetailedPostResponseDto;
import org.hca.blogproject.dto.response.PostResponseDto;
import org.hca.blogproject.entity.Comment;
import org.hca.blogproject.entity.Post;
import org.hca.blogproject.entity.User;
import org.hca.blogproject.mapper.customMapper.CustomPostMapper;
import org.hca.blogproject.repository.CommentRepository;
import org.hca.blogproject.repository.PostRepository;
import org.hca.blogproject.rules.CategoryBusinessRules;
import org.hca.blogproject.rules.PostBusinessRules;
import org.hca.blogproject.rules.UserBusinessRules;
import org.springframework.stereotype.Service;

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


    public PostResponseDto saveDto(PostRequestDto request) {
        Post postToSave = checkPostWithRulesToSave(request);

        postRepository.save(postToSave);
        return customMapper.postToPostResponseDto(postToSave);
    }

    public PostResponseDto updateDto(Long id, PostRequestDto request) {
        postBusinessRules.checkIfExistsById(id);
        Post postToUpdate = checkPostWithRulesToSave(request);

        postToUpdate.setId(id);
        postRepository.save(postToUpdate);
        return customMapper.postToPostResponseDto(postToUpdate);
    }

    public DetailedPostResponseDto findDetailedDtoById(Long id) {
        postBusinessRules.checkIfExistsById(id);

        return customPostMapper.postToDetailedPostResponseDto(postRepository.findById(id).get());//checked at business rules
    }

    public List<PostResponseDto> findAllDto() {
        return  postRepository.findAll()
                .stream()
                .map(customMapper::postToPostResponseDto)
                .collect(Collectors.toList());
    }

    public PostResponseDto deleteDto(Long id) {
        postBusinessRules.checkIfExistsById(id);

        Post postToDelete = postRepository.findById(id).get();//checked at business rules
        postToDelete.setCategories(new ArrayList<>());
        postToDelete.setLikes(new ArrayList<>());
        List<Comment> comments = postToDelete.getComments();
        postToDelete.setComments(new ArrayList<>());
        commentRepository.deleteAll(comments);
        postRepository.delete(postToDelete);
        return customMapper.postToPostResponseDto(postToDelete);
    }

    public List<PostResponseDto> findByUserId(Long id) {
        userBusinessRules.checkIfExistsById(id);
        userBusinessRules.checkIfUserDeleted(id);

        List<Post> postList = postRepository.findByUserId(id);
        postBusinessRules.checkIfPostListEmpty(postList);

        return customPostMapper.postListToPostResponseDtoList(postList);
    }

    public List<PostResponseDto> findByCategoryId(Long id) {
        categoryBusinessRules.checkIfExistsById(id);
        categoryBusinessRules.checkIfCategoryDeleted(id);

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
        categoryBusinessRules.checkIfCategoryDeleted(name);

        List<Post> postList = postRepository.findByCategoryName(name);
        postBusinessRules.checkIfPostListEmpty(postList);

        return customPostMapper.postListToPostResponseDtoList(postList);
    }

    public DetailedPostResponseDto like(Long userId, Long postId) {
        Post dumyPost = new Post();
        User dumyUser = new User();
        userBusinessRules.checkIfExistsById(userId,dumyUser);
        userBusinessRules.checkIfUserDeleted(userId);
        postBusinessRules.checkIfExistsById(postId,dumyPost);
        postBusinessRules.checkIfPostLikedByUser(userId, postId);

        Post postToLike = postRepository.findById(postId).get();//checked at business rules
        postToLike.getLikes().add(userService.findById(userId).get()); //checked at business rules
        postRepository.save(postToLike);
        return customPostMapper.postToDetailedPostResponseDto(postToLike);
    }

    public DetailedPostResponseDto unlike(Long userId, Long postId) {
        Post dumyPost = new Post();
        User dumyUser = new User();
        userBusinessRules.checkIfExistsById(userId,dumyUser);
        userBusinessRules.checkIfUserDeleted(userId);
        postBusinessRules.checkIfExistsById(postId,dumyPost);
        postBusinessRules.checkIfPostAlreadyLikedByUser(userId, postId);

        Post postToLike = postRepository.findById(postId).get();//checked at business rules
        postToLike.setLikes(postToLike.getLikes().stream().filter(userToUnlike -> !userToUnlike.getId().equals((userId))).collect(Collectors.toList()));
        postRepository.save(postToLike);
        return customPostMapper.postToDetailedPostResponseDto(postToLike);
    }

    public Post findById(Long id) {
        postBusinessRules.checkIfExistsById(id);

        return postRepository.findById(id).get();//checked at business rules
    }

    public List<PostResponseDto> getPostsInChronologicalOrder() {
        return customPostMapper.postListToPostResponseDtoList(postRepository.findAllByOrderByCreatedAtDesc());
    }

    private Post checkPostWithRulesToSave(PostRequestDto request) {
        postBusinessRules.checkIfNull(request.title());
        postBusinessRules.checkIfNull(request.content());
        userBusinessRules.checkIfNull(request.userId());
        categoryBusinessRules.checkIfListEmpty(request.categories());
        userBusinessRules.checkIfExistsById(request.userId());
        userBusinessRules.checkIfUserDeleted(request.userId());
        Post postToCheck = customMapper.postRequestDtoToPost(request);
        postBusinessRules.validatePostFieldLengths(postToCheck);
        return postToCheck;
    }

//    public PostResponseDto findDtoById(Long id) {
//        postBusinessRules.checkIfNull(id);
//        postBusinessRules.checkIfExistsById(id);
//
//        return customMapper.postToPostResponseDto(postRepository.findById(id).get());//checked at business rules
//    }

//    public Post save(Post post) {
//        userBusinessRules.checkIfNull(post.getUser().getId());
//        userBusinessRules.checkIfExistsById(post.getUser().getId());
//        userBusinessRules.checkIfUserDeleted(post.getUser().getId());
//        postBusinessRules.checkIfNull(post.getTitle());
//        postBusinessRules.checkIfNull(post.getContent());
//
//        return postRepository.save(post);
//    }
}