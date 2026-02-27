package com.blog_yonetim_sistemi.backend.mapper;

import com.blog_yonetim_sistemi.backend.dto.request.UserRequest;
import com.blog_yonetim_sistemi.backend.dto.response.UserResponse;
import com.blog_yonetim_sistemi.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Entity -> Response
    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);

    // Request -> Entity (Register için)
    User toEntity(UserRequest request);

    // Update için mevcut entity'yi güncelle
    void updateEntityFromRequest(UserRequest request,
                                 @MappingTarget User user);
}