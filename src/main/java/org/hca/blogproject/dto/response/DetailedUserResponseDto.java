package org.hca.blogproject.dto.response;


import lombok.Builder;

import java.util.List;
@Builder
public record DetailedUserResponseDto(Long id,
                                      String firstname,
                                      String lastname,
                                      String email,
                                      String password,
                                      String createdAt,
                                      List<PostResponseDto> posts) {
}
