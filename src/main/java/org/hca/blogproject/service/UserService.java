package org.hca.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.UserRequestDto;
import org.hca.blogproject.dto.response.DetailedUserResponseDto;
import org.hca.blogproject.dto.response.UserResponseDto;
import org.hca.blogproject.entity.User;
import org.hca.blogproject.mapper.customMapper.CustomUserMapper;
import org.hca.blogproject.mapper.UserMapper;
import org.hca.blogproject.repository.UserRepository;
import org.hca.blogproject.rules.UserBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final UserBusinessRules userBusinessRules;
    private final UserMapper userMapper;
    private final CustomUserMapper customUserMapper;
    
    public UserResponseDto saveDto(UserRequestDto request) {
        User userToSave = checkUserWithRulesToSave(request);
        userBusinessRules.checkIfEmailTakenBySomeoneElse(userToSave.getEmail());

        userRepository.save(userToSave);
        return UserMapper.INSTANCE.userToUserResponseDto(userToSave);
    }


    public UserResponseDto updateDto(Long id, UserRequestDto request) {
        userBusinessRules.checkIfExistsById(id);
        userBusinessRules.checkIfUserDeleted(id);
        userBusinessRules.checkIfEmailTakenBySomeoneElse(request.email(), id);
        User userToUpdate = checkUserWithRulesToSave(request);

        userToUpdate.setId(id);
        userRepository.save(userToUpdate);
        return UserMapper.INSTANCE.userToUserResponseDto(userToUpdate);
    }

    public DetailedUserResponseDto findDtoById(Long id){
        userBusinessRules.checkIfExistsById(id);
        userBusinessRules.checkIfUserDeleted(id);

        return customUserMapper.userToDetailedUserResponseDto(userRepository.findById(id).get());//checked at business rules
    }

    public List<UserResponseDto> findAllDto() {
        return  userRepository.findAll()
                .stream()
                .filter(user ->!user.isDeleted())
                .map(UserMapper.INSTANCE::userToUserResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto setToDeletedDto(Long id) {
        userBusinessRules.checkIfExistsById(id);
        userBusinessRules.checkIfUserDeleted(id);

        User userToDelete = userRepository.findById(id).get();//checked at business rules
        userToDelete.setDeleted(true);
        userToDelete.setDeletedAt(LocalDateTime.now().toString());
        userRepository.save(userToDelete);
        return UserMapper.INSTANCE.userToUserResponseDto(userToDelete);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public UserResponseDto reActivateUser(Long id) {
        userBusinessRules.checkIfExistsById(id);
        userBusinessRules.checkIfUserAlreadyActive(id);

        User user = userRepository.findById(id).get(); //checked at business rules
        user.setDeleted(false);
        user.setDeletedAt(null);
        return UserMapper.INSTANCE.userToUserResponseDto(userRepository.save(user));
    }

    private User checkUserWithRulesToSave(UserRequestDto request) {
        User userToCheck = UserMapper.INSTANCE.userRequestDtoToUser(request);
        userBusinessRules.validateUserFieldLengths(userToCheck);
        userBusinessRules.checkIfEmailValid(userToCheck.getEmail());
        userBusinessRules.checkIfNull(userToCheck.getPassword());
        return userToCheck;
    }

//    public UserResponseDto deleteDto(Long id) {
//        userBusinessRules.checkIfExistsById(id);
//
//        User userToDelete = userRepository.findById(id).get();//checked at business rules
//        userRepository.delete(userToDelete);
//        return UserMapper.INSTANCE.userToUserResponseDto(userToDelete);
//    }
}