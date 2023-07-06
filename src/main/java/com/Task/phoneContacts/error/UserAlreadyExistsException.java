package com.Task.phoneContacts.error;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String name) {
        super("User already exists : " + name);
    }

}