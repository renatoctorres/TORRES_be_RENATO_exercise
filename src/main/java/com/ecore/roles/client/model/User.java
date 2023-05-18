package com.ecore.roles.client.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class User {

    private UUID id;

    private String firstName;

    private String lastName;

    private String displayName;

    private String avatarUrl;

    private String location;
}
