package org.hca.blogproject.rules;

import org.hca.blogproject.entity.Comment;
import org.hca.blogproject.exception.BusinessException;
import org.hca.blogproject.exception.ErrorType;
import org.hca.blogproject.repository.CommentRepository;
import org.hca.blogproject.rules.manager.BusinessRulesManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * @BusinessRules
 */
@Service
public class CommentBusinessRules extends BusinessRulesManager<Comment,Long> {
    private final CommentRepository commentRepository;

    public CommentBusinessRules(JpaRepository<Comment, Long> jpaRepository, CommentRepository commentRepository) {
        super(jpaRepository);
        this.commentRepository = commentRepository;
    }
    public void validateCommentFieldLengths(Comment comment){
        try{
            Field contentField = comment.getClass().getDeclaredField("content");
            validateFieldLength(comment.getContent(),contentField);

        }catch (NoSuchFieldException e){
            throw new BusinessException(ErrorType.FIELD_ERROR);
        }
    }
}
