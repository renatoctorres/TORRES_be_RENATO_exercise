package com.ecore.roles.web.dto;

import com.ecore.roles.client.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.UUID;
@Builder
public class UserDto {
    private UUID id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    private String displayName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String avatarUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String location;

    public static UserDto fromModel(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .displayName(user.getDisplayName())
                .avatarUrl(user.getAvatarUrl())
                .location(user.getLocation())
                .build();
    }
}
