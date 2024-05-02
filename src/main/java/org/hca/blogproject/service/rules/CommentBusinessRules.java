package org.hca.blogproject.service.rules;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.entity.Comment;
import org.hca.blogproject.exception.BusinessException;
import org.hca.blogproject.exception.ErrorType;
import org.hca.blogproject.repository.CommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
/**
 * @BusinessRules
 */
@Service
public class CommentBusinessRules extends BusinessRulesManager<Comment,Long>{
    private final CommentRepository commentRepository;

    public CommentBusinessRules(JpaRepository<Comment, Long> jpaRepository, CommentRepository commentRepository) {
        super(jpaRepository);
        this.commentRepository = commentRepository;
    }
}
