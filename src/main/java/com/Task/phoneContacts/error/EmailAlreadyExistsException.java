package com.Task.phoneContacts.error;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException() {
        super("Email already exists.");
    }
    
    public EmailAlreadyExistsException(String address) {
        super("Email already exists: " + address);
    }
}