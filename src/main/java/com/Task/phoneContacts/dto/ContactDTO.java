package com.Task.phoneContacts.dto;

public class ContactDTO {
	
	private String name; 
	private String emails[];
	private String phones[];
	
	public ContactDTO() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getEmails() {
		return emails;
	}

	public void setEmails(String emails[]) {
		this.emails = emails;
	}

	public String[] getPhones() {
		return phones;
	}

	public void setPhones(String phones[]) {
		this.phones = phones;
	} 
	
	
}