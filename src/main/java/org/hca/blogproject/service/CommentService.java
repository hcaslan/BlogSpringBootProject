package org.hca.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.CommentRequestDto;
import org.hca.blogproject.dto.response.CommentResponseDto;
import org.hca.blogproject.dto.response.DetailedCommentResponseDto;
import org.hca.blogproject.entity.Comment;
import org.hca.blogproject.entity.Post;
import org.hca.blogproject.entity.User;
import org.hca.blogproject.mapper.CustomCommentMapper;
import org.hca.blogproject.mapper.CustomUserMapper;
import org.hca.blogproject.repository.CommentRepository;
import org.hca.blogproject.service.rules.CommentBusinessRules;
import org.hca.blogproject.service.rules.PostBusinessRules;
import org.hca.blogproject.service.rules.UserBusinessRules;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserBusinessRules userBusinessRules;
    private final PostBusinessRules postBusinessRules;
    private final PostService postService;
    private final UserService userService;
    private final CustomCommentMapper customCommentMapper;
    private final CommentBusinessRules commentBusinessRules;

    public DetailedCommentResponseDto comment(CommentRequestDto request) {
        userBusinessRules.checkIfUserExistsById(request.userId());
        userBusinessRules.checkIfUserDeleted(request.userId());
        postBusinessRules.checkIfPostExistsById(request.postId());
        //postBusinessRules.checkIfPostDeleted(postId);
        Post post = postService.findById(request.postId());//checked at business rules
        User user = userService.findById(request.userId()).get(); //checked at business rules
        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(request.content())
                .build();
        commentRepository.save(comment);
        post.getComments().add(comment);
        postService.save(post);
        return customCommentMapper.commentToDetailedCommentResponseDto(comment);
    }

    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }

    public CommentResponseDto delete(Long id) {
        commentBusinessRules.checkIfCommentExistsById(id);

        Comment commentToDelete = commentRepository.findById(id).get();//checked at business rules
        CommentResponseDto commentResponseDto = customCommentMapper.commentToCommentResponseDto(commentToDelete);
        commentRepository.delete(commentToDelete);
        return commentResponseDto;
    }

    public List<DetailedCommentResponseDto> findAll() {
        return customCommentMapper.commentListToDetailedCommentResponseDtoList(commentRepository.findAll());
    }
}
