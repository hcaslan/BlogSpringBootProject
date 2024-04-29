package org.hca.blogproject.mapper;

import org.hca.blogproject.dto.request.PostRequestDto;
import org.hca.blogproject.dto.response.PostResponseDto;
import org.hca.blogproject.entity.Category;
import org.hca.blogproject.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class );

    //PostRequestDto
//    Post postRequestDtoToPost(PostRequestDto dto);
//    PostRequestDto postToPostRequestDto(Post post);
//
//
//    //PostResponseDto
//    Post postResponseDtoToPost(PostResponseDto dto);
//    PostResponseDto postToPostResponseDto(Post post);

    public static PostResponseDto mapToDto(Post post) {
        List<String> categoryNames = post.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toList());

        return new PostResponseDto(post.getTitle(),
                post.getContent(),
                post.getUser().getId(),
                categoryNames);
    }
}
