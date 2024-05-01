package org.hca.blogproject.service.rules;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.entity.Post;
import org.hca.blogproject.exception.BusinessException;
import org.hca.blogproject.exception.ErrorType;
import org.hca.blogproject.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * @BusinessRules
 */
@Service
@RequiredArgsConstructor
public class PostBusinessRules {
    private final PostRepository postRepository;
    private final UserBusinessRules userBusinessRules;

    public void checkIfPostExistsById(Long id) {
        if (!postRepository.existsById(id)) throw new BusinessException(ErrorType.POST_NOT_FOUND);
    }

    public void checkIfPostDeleted(Long id){
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            if(post.isDeleted()) throw new BusinessException(ErrorType.POST_DELETED);
        }
    }

    public void checkIfPostListEmpty(List<Post> posts){
        if(posts.isEmpty()) throw new BusinessException(ErrorType.POST_NOT_FOUND);
    }

    public void checkIfPostLikedByUser(Long userId, Long postId) {
        checkIfPostExistsById(postId);
        userBusinessRules.checkIfUserExistsById(userId);
        if(postRepository.findById(postId).get().getLikes().stream().anyMatch(user -> user.getId().equals(userId))) throw new BusinessException(ErrorType.POST_ALREADY_LIKED);//checked at business rules
    }
    public void checkIfPostAlreadyLikedByUser(Long userId, Long postId) {
        checkIfPostExistsById(postId);
        userBusinessRules.checkIfUserExistsById(userId);
        if(postRepository.findById(postId).get().getLikes().stream().noneMatch(user -> user.getId().equals(userId))) throw new BusinessException(ErrorType.POST_NOT_LIKED);//checked at business rules
    }
}
