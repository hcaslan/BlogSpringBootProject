package org.hca.blogproject.service;

import org.hca.blogproject.dto.request.UserRequestDto;
import org.hca.blogproject.dto.response.UserResponseDto;
import org.hca.blogproject.entity.User;
import org.hca.blogproject.mapper.UserMapper;
import org.hca.blogproject.repository.UserRepository;
import org.hca.blogproject.service.rules.UserBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService{
    private final UserRepository userRepository;
    private final UserBusinessRules businessRules;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserBusinessRules businessRules, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.businessRules = businessRules;
        this.userMapper = userMapper;
    }
    public UserResponseDto saveDto(UserRequestDto dto) {
        User user = UserMapper.INSTANCE.userRequestDtoToUser(dto);
        businessRules.checkIfEmailTakenBySomeoneElse(user.getEmail(),user.getId());
        userRepository.save(user);
        return UserMapper.INSTANCE.userToUserResponseDto(user);
    }
    public UserResponseDto updateDto(Long id, UserRequestDto request) {
        businessRules.checkIfEmailTakenBySomeoneElse(request.email(),id);
        businessRules.checkIfUserDeleted(id);
        businessRules.checkIfUserExistsById(id);
        User userToUpdate = UserMapper.INSTANCE.userRequestDtoToUser(request);
        userToUpdate.setId(id);
        userRepository.save(userToUpdate);
        return UserMapper.INSTANCE.userToUserResponseDto(userToUpdate);
    }
    public UserResponseDto findDtoById(Long id){
        businessRules.checkIfUserDeleted(id);
        businessRules.checkIfUserExistsById(id);
        return UserMapper.INSTANCE.userToUserResponseDto(userRepository.findById(id).get());//checked at business rules
    }
    public List<UserResponseDto> findAllDto() {
        return  userRepository.findAll()
                .stream()
                .filter(user ->!user.isDeleted())
                .map(UserMapper.INSTANCE::userToUserResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto setToDeletedDto(Long id) {
        businessRules.checkIfUserDeleted(id);
        businessRules.checkIfUserExistsById(id);
        User userToDelete = userRepository.findById(id).get();//checked at business rules
        userToDelete.setDeleted(true);
        userToDelete.setDeletedAt(LocalDateTime.now().toString());
        userRepository.save(userToDelete);
        return UserMapper.INSTANCE.userToUserResponseDto(userToDelete);
    }

    public UserResponseDto deleteDto(Long id) {
        businessRules.checkIfUserExistsById(id);
        User userToDelete = userRepository.findById(id).get();//checked at business rules
        userRepository.delete(userToDelete);
        return UserMapper.INSTANCE.userToUserResponseDto(userToDelete);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}