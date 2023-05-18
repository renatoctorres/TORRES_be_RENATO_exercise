package com.ecore.roles.service.impl;

import com.ecore.roles.client.UsersClient;
import com.ecore.roles.client.model.User;
import com.ecore.roles.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * User Service Implementation
 */
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersClient usersClient;

    /**
     *
     * @param id User ID
     * @return User Entity from DB
     */
    public User getUser(UUID id) {
        return usersClient.getUser(id).getBody();
    }

    /**
     *
     * @return List of All Users
     */
    public List<User> getUsers() {
        return usersClient.getUsers().getBody();
    }
}
