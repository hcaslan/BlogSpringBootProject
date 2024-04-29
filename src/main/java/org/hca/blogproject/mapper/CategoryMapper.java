package org.hca.blogproject.mapper;

import org.hca.blogproject.dto.request.CategoryRequestDto;
import org.hca.blogproject.dto.request.UserRequestDto;
import org.hca.blogproject.dto.response.CategoryResponseDto;
import org.hca.blogproject.dto.response.UserResponseDto;
import org.hca.blogproject.entity.Category;
import org.hca.blogproject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper{
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class );

    //CategoryRequestDto
    Category categoryRequestDtoToCategory(CategoryRequestDto dto);
    CategoryRequestDto categoryToCategoryRequestDto(Category category);


    //CategoryResponseDto
    Category categoryResponseDtoToCategory(CategoryResponseDto dto);
    CategoryResponseDto categoryToCategoryResponseDto(Category category);

}