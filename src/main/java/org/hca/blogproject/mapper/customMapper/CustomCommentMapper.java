package org.hca.blogproject.mapper.customMapper;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.CommentRequestDto;
import org.hca.blogproject.dto.response.CommentResponseDto;
import org.hca.blogproject.dto.response.DetailedCommentResponseDto;
import org.hca.blogproject.entity.Comment;
import org.hca.blogproject.entity.Post;
import org.hca.blogproject.entity.User;
import org.hca.blogproject.service.PostService;
import org.hca.blogproject.service.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @CustomMapper
 */
@RequiredArgsConstructor
@Component
public class CustomCommentMapper {
    private final CustomPostMapper customPostMapper;
    private final UserService userService;
    private final PostService postService;
    private final String DELETED_USER = "DELETED_USER";
    public DetailedCommentResponseDto commentToDetailedCommentResponseDto(Comment comment) {
        return DetailedCommentResponseDto.builder()
                .id(comment.getId())
                .commentContent(comment.getContent())
                .post(customPostMapper.postToPostResponseDto(comment.getPost()))
                .commenterName(comment.getUser().isDeleted() ? DELETED_USER : getUserFirstAndLastName(comment.getUser()))
                .createdAt(comment.getCreatedAt())
                .build();
    }
    public CommentResponseDto commentToCommentResponseDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .commentContent(comment.getContent())
                .commenterName(comment.getUser().isDeleted() ? DELETED_USER : getUserFirstAndLastName(comment.getUser()))
                .build();
    }
    public Comment commentRequestDtoToComment(CommentRequestDto commentRequestDto) {
        Post post = postService.findById(commentRequestDto.postId());//checked at business rules
        User user = userService.findById(commentRequestDto.userId()).get(); //checked at business rules
        return Comment.builder()
                .post(post)
                .user(user)
                .content(commentRequestDto.content())
                .build();
    }
    public List<DetailedCommentResponseDto> commentListToDetailedCommentResponseDtoList(List<Comment> commentList){
        List<DetailedCommentResponseDto> results = new ArrayList<>();

        commentList.forEach(comment -> {
            results.add(commentToDetailedCommentResponseDto(comment));
        });
        return results;
    }

    private String getUserFirstAndLastName(User user) {
        return user.getFirstname() + " " + user.getLastname();
    }
}
