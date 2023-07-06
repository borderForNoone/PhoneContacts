package com.Task.phoneContacts.error;

public class ContactNotFoundException extends RuntimeException {

    public ContactNotFoundException(Long id) {
        super("Contact id not found : " + id);
    }

}