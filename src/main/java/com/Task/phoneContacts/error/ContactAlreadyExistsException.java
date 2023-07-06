package com.Task.phoneContacts.error;

public class ContactAlreadyExistsException extends RuntimeException {

    public ContactAlreadyExistsException(String name) {
        super("Contact id already exists : " + name);
    }

}