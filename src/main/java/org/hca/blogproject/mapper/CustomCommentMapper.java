package org.hca.blogproject.mapper;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.response.CommentResponseDto;
import org.hca.blogproject.dto.response.DetailedCommentResponseDto;
import org.hca.blogproject.dto.response.DetailedPostResponseDto;
import org.hca.blogproject.dto.response.PostResponseDto;
import org.hca.blogproject.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @CustomMapper
 */
@RequiredArgsConstructor
@Component
public class CustomCommentMapper {
    private final CustomPostMapper customPostMapper;
    private final String DELETED_USER = "DELETED_USER";
    public DetailedCommentResponseDto commentToDetailedCommentResponseDto(Comment comment) {
        return DetailedCommentResponseDto.builder()
                .id(comment.getId())
                .commentContent(comment.getContent())
                .post(customPostMapper.postToPostResponseDto(comment.getPost()))
                .commenterName(comment.getUser().isDeleted() ? DELETED_USER : getUserFirstAndLastName(comment))
                .createdAt(comment.getCreatedAt())
                .build();
    }
    public CommentResponseDto commentToCommentResponseDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .commentContent(comment.getContent())
                .commenterName(comment.getUser().isDeleted() ? DELETED_USER : getUserFirstAndLastName(comment))
                .build();
    }
    public List<DetailedCommentResponseDto> commentListToDetailedCommentResponseDtoList(List<Comment> commentList){
        List<DetailedCommentResponseDto> results = new ArrayList<>();

        commentList.forEach(comment -> {
            results.add(commentToDetailedCommentResponseDto(comment));
        });
        return results;
    }

    private String getUserFirstAndLastName(Comment comment) {
        return comment.getUser().getFirstname() + " " + comment.getUser().getLastname();
    }
}
