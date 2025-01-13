package com.devteria.identity_sevice.mapper;

import com.devteria.identity_sevice.dto.request.UserCreationRequest;
import com.devteria.identity_sevice.dto.request.UserUpdateRequest;
import com.devteria.identity_sevice.dto.response.UserResponse;
import com.devteria.identity_sevice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest user);

    @Mapping(source = "firstName", target = "lastName")
    // Như thế này nó sẽ map 2 field trên trùng nhau.

    @Mapping(target = "firstName", ignore = true)
        // Như thế này nó sẽ ignore field firstName đi và giá trị = null.
    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
