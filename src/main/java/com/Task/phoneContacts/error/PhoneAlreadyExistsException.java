package com.Task.phoneContacts.error;

public class PhoneAlreadyExistsException extends RuntimeException {

	public PhoneAlreadyExistsException() {
        super("Phone already exists.");
    }
    
    public PhoneAlreadyExistsException(String address) {
        super("Phone already exists: " + address);
    }

}