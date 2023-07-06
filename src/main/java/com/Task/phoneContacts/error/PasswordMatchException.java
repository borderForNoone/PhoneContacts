package com.Task.phoneContacts.error;

public class PasswordMatchException extends RuntimeException {

    public PasswordMatchException() {
        super("Passwords must be the same.");
    }

}