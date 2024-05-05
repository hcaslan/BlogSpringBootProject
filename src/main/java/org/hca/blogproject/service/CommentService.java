package org.hca.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.CommentRequestDto;
import org.hca.blogproject.dto.response.CommentResponseDto;
import org.hca.blogproject.dto.response.DetailedCommentResponseDto;
import org.hca.blogproject.entity.Comment;
import org.hca.blogproject.entity.Post;
import org.hca.blogproject.entity.User;
import org.hca.blogproject.mapper.customMapper.CustomCommentMapper;
import org.hca.blogproject.repository.CommentRepository;
import org.hca.blogproject.rules.CommentBusinessRules;
import org.hca.blogproject.rules.PostBusinessRules;
import org.hca.blogproject.rules.UserBusinessRules;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserBusinessRules userBusinessRules;
    private final PostBusinessRules postBusinessRules;
    private final CustomCommentMapper customCommentMapper;
    private final CommentBusinessRules commentBusinessRules;

    public DetailedCommentResponseDto comment(CommentRequestDto request) {
        Comment commentToSave = checkCommentWithRulesToSave(request);

        commentRepository.save(commentToSave);
        return customCommentMapper.commentToDetailedCommentResponseDto(commentToSave);
    }

    public CommentResponseDto updateDto(Long id, CommentRequestDto request) {
        commentBusinessRules.checkIfExistsById(id);
        Comment commentToUpdate = checkCommentWithRulesToSave(request);

        commentToUpdate.setId(id);
        commentRepository.save(commentToUpdate);
        return customCommentMapper.commentToCommentResponseDto(commentToUpdate);
    }

    public CommentResponseDto delete(Long id) {
        commentBusinessRules.checkIfExistsById(id);

        Comment commentToDelete = commentRepository.findById(id).get();//checked at business rules
        CommentResponseDto commentResponseDto = customCommentMapper.commentToCommentResponseDto(commentToDelete);
        commentRepository.delete(commentToDelete);
        return commentResponseDto;
    }

    public List<DetailedCommentResponseDto> findAll() {
        return customCommentMapper.commentListToDetailedCommentResponseDtoList(commentRepository.findAll());
    }

    public DetailedCommentResponseDto findDetailedDtoById(Long id) {
        commentBusinessRules.checkIfExistsById(id);

        return customCommentMapper.commentToDetailedCommentResponseDto(commentRepository.findById(id).get()); //checked at business rules
    }

    private Comment checkCommentWithRulesToSave(CommentRequestDto request) {
        Post dumyPost = new Post();
        User dumyUser = new User();
        userBusinessRules.checkIfNull(request.userId());
        postBusinessRules.checkIfNull(request.postId());
        commentBusinessRules.checkIfNull(request.content());
        userBusinessRules.checkIfExistsById(request.userId(),dumyUser);
        userBusinessRules.checkIfUserDeleted(request.userId());
        postBusinessRules.checkIfExistsById(request.postId(),dumyPost);

        Comment commentToCheck = customCommentMapper.commentRequestDtoToComment(request);
        commentBusinessRules.validateCommentFieldLengths(commentToCheck);
        return commentToCheck;
    }
}
