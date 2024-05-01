package org.hca.blogproject.service.rules;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.exception.BusinessException;
import org.hca.blogproject.exception.ErrorType;
import org.hca.blogproject.repository.CommentRepository;
import org.springframework.stereotype.Service;
/**
 * @BusinessRules
 */
@Service
@RequiredArgsConstructor
public class CommentBusinessRules {
    private final CommentRepository commentRepository;
    public void checkIfCommentExistsById(Long id) {
        if (!commentRepository.existsById(id)) throw new BusinessException(ErrorType.COMMENT_NOT_FOUND);
    }
}
