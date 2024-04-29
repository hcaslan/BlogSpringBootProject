package org.hca.blogproject.mapper;

import org.hca.blogproject.dto.request.UserRequestDto;
import org.hca.blogproject.dto.response.UserResponseDto;
import org.hca.blogproject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper{
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class );

    //T dtoToEntity (Dto dto);
    //Dto entityToDto (T entity); <Dto,T>
    //UpdateUserRequestDto
    User userRequestDtoToUser(UserRequestDto dto);
    UserRequestDto userToUserRequestDto(User user);


    //UserResponseDto
    User userResponseDtoToUser (UserResponseDto dto);
    UserResponseDto userToUserResponseDto(User user);

}
