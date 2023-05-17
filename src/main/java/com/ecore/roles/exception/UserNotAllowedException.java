package com.ecore.roles.exception;

public class UserNotAllowedException extends RuntimeException{
    public <T> UserNotAllowedException(Class<T> resource) {
        super("Invalid 'Membership' object. The provided user doesn't belong to the provided team.");
    }
}
