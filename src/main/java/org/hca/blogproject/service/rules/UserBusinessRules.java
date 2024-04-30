package org.hca.blogproject.service.rules;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.entity.User;
import org.hca.blogproject.exception.BusinessException;
import org.hca.blogproject.exception.ErrorType;
import org.hca.blogproject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**
 * @BusinessRules
 */
@Service
@RequiredArgsConstructor
public class UserBusinessRules {
    private final UserRepository userRepository;
    public void checkIfUserExistsById(Long id) {
        if (!userRepository.existsById(id)) throw new BusinessException(ErrorType.USER_NOT_FOUND);
    }
    public void checkIfEmailTakenBySomeoneElse(String email, Long user_id) {
        if (userRepository.existsByEmailAndIdNot(email, user_id)) throw new BusinessException(ErrorType.EMAIL_ALREADY_EXISTS);
    }
    public void checkIfEmailTakenBySomeoneElse(String email) {
        if (userRepository.existsByEmail(email)) throw new BusinessException(ErrorType.EMAIL_ALREADY_EXISTS);
    }
    public void checkIfUserDeleted(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(user.isDeleted()) throw new BusinessException(ErrorType.USER_DELETED);
        }
    }
}

