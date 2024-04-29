package org.hca.blogproject.service.rules;

import lombok.AllArgsConstructor;
import org.hca.blogproject.entity.User;
import org.hca.blogproject.exception.BusinessException;
import org.hca.blogproject.exception.ErrorType;
import org.hca.blogproject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserBusinessRules {
    private final UserRepository repository;
    public void checkIfUserExistsById(Long id) {
        if (!repository.existsById(id)) throw new BusinessException(ErrorType.USER_NOT_FOUND);
    }
    public void checkIfEmailTakenBySomeoneElse(String email, Long user_id) {
        if (repository.existsByEmailAndIdNot(email, user_id)) throw new BusinessException(ErrorType.EMAIL_ALREADY_EXISTS);
        //if (repository.existsByEmail(email)) throw new BusinessException(ErrorType.EMAIL_ALREADY_EXISTS);
    }
    public void checkIfUserDeleted(Long id){
        Optional<User> optionalUser = repository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(user.isDeleted()) throw new BusinessException(ErrorType.USER_DELETED);
        }
    }
}

