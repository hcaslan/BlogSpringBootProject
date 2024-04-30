package org.hca.blogproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
/**
 * PostRequestDto has List<String> categories as a field,
 * but Post Entity has List<Category> categories as a field,
 * so I defined a custom mapper and I do the conversions there.
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper{
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class );

}
