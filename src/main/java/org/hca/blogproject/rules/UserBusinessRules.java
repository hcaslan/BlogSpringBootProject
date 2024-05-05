package org.hca.blogproject.rules;

import org.hca.blogproject.entity.User;
import org.hca.blogproject.exception.BusinessException;
import org.hca.blogproject.exception.ErrorType;
import org.hca.blogproject.repository.UserRepository;
import org.hca.blogproject.rules.manager.BusinessRulesManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.apache.commons.validator.routines.EmailValidator;

import java.lang.reflect.Field;
import java.util.Optional;
/**
 * @BusinessRules
 */
@Service
public class UserBusinessRules extends BusinessRulesManager<User,Long> {
    private final UserRepository userRepository;


    public UserBusinessRules(JpaRepository<User, Long> jpaRepository, UserRepository userRepository) {
        super(jpaRepository);
        this.userRepository = userRepository;
    }
    public void validateUserFieldLengths(User user){
        try{
            Field firstnameField = user.getClass().getDeclaredField("firstname");
            validateFieldLength(user.getFirstname(),firstnameField);
            Field lastnameField = user.getClass().getDeclaredField("lastname");
            validateFieldLength(user.getLastname(),lastnameField);
            Field emailField = user.getClass().getDeclaredField("email");
            validateFieldLength(user.getEmail(),emailField);
            Field passwordField = user.getClass().getDeclaredField("password");
            validateFieldLength(user.getPassword(),passwordField);
        }catch (NoSuchFieldException e){
            throw new BusinessException(ErrorType.FIELD_ERROR);
        }
    }
    public void checkIfEmailValid(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        if(!validator.isValid(email)) throw new BusinessException(ErrorType.EMAIL_NOT_VALID);
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

    public void checkIfUserAlreadyActive(Long id) {
        checkIfExistsById(id);
        if(!userRepository.findById(id).get().isDeleted()) throw new BusinessException(ErrorType.USER_ALREADY_ACTIVE); //checked at business rules
    }
}

