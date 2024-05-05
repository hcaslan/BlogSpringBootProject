package org.hca.blogproject.rules;

import org.hca.blogproject.entity.Post;
import org.hca.blogproject.exception.BusinessException;
import org.hca.blogproject.exception.ErrorType;
import org.hca.blogproject.repository.PostRepository;
import org.hca.blogproject.rules.manager.BusinessRulesManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @BusinessRules
 */
@Service
public class PostBusinessRules extends BusinessRulesManager<Post,Long> {
    private final PostRepository postRepository;
    private final UserBusinessRules userBusinessRules;

    public PostBusinessRules(JpaRepository<Post, Long> jpaRepository, PostRepository postRepository, UserBusinessRules userBusinessRules) {
        super(jpaRepository);
        this.postRepository = postRepository;
        this.userBusinessRules = userBusinessRules;
    }
    public void validatePostFieldLengths(Post post){
        try{
            Field contentField = post.getClass().getDeclaredField("content");
            validateFieldLength(post.getContent(),contentField);
            Field titleField = post.getClass().getDeclaredField("title");
            validateFieldLength(post.getTitle(),titleField);
        }catch (NoSuchFieldException e){
            throw new BusinessException(ErrorType.FIELD_ERROR);
        }
    }

    public void checkIfPostListEmpty(List<Post> posts){
        if(posts.isEmpty()) throw new BusinessException(ErrorType.POST_NOT_FOUND);
    }

    public void checkIfPostLikedByUser(Long userId, Long postId) {
        checkIfExistsById(postId);
        userBusinessRules.checkIfExistsById(userId);
        if(postRepository.findById(postId).get().getLikes().stream().anyMatch(user -> user.getId().equals(userId))) throw new BusinessException(ErrorType.POST_ALREADY_LIKED);//checked at business rules
    }
    public void checkIfPostAlreadyLikedByUser(Long userId, Long postId) {
        checkIfExistsById(postId);
        userBusinessRules.checkIfExistsById(userId);
        if(postRepository.findById(postId).get().getLikes().stream().noneMatch(user -> user.getId().equals(userId))) throw new BusinessException(ErrorType.POST_NOT_LIKED);//checked at business rules
    }
    //    public void checkIfPostDeleted(Long id){
//        Optional<Post> optionalPost = postRepository.findById(id);
//        if(optionalPost.isPresent()){
//            Post post = optionalPost.get();
//            if(post.isDeleted()) throw new BusinessException(ErrorType.POST_DELETED);
//        }
//    }
}
