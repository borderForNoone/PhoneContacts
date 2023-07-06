package com.Task.phoneContacts.model;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Name may not be null")
    @Size(min=3, max=25)
    private String name;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ContactEmail> contactEmails;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ContactPhone> contactPhones;
  
    public Contact() {}

    public Contact(String name) {
        this.name = name;

        contactEmails = new HashSet<>();
        contactPhones = new HashSet<>();
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setContactEmails(Set<ContactEmail> contactEmails) {
        this.contactEmails = contactEmails;
    }
    public Set<ContactEmail> getContactEmails() {
        return contactEmails;
    }

    public void setContactPhones(Set<ContactPhone> contactPhones) {
        this.contactPhones = contactPhones;
    }
    public Set<ContactPhone> getContactPhones() {
        return contactPhones;
    }
      
    @Override
    public String toString() {
    	String result = "Contact[id=" + id + " name=" + name + "]\n";
        if (contactEmails != null) {
        	result += "emails: ";
            for(ContactEmail e : contactEmails) {
                result += "{ " + e.getAddress() + " } ";
            }
            result += "phones: ";
            for(ContactPhone e : contactPhones) {
                result += "{ " + e.getNumber() + " } ";
            }
        }

        return result;
    }
    
    @Override
    public int hashCode() {
    	int hash = 0;
    	hash ^=  name.hashCode();
    	hash ^=  contactEmails.hashCode();
        return hash;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        
        Contact other = (Contact) obj;
 
        if(this.contactEmails.size() != other.contactEmails.size()) {
        	return false;
        }
        
        ContactEmail[] thisEmails = contactEmails.toArray(new ContactEmail[0]); 
        ContactEmail[] otherEmails = other.getContactEmails().toArray(new ContactEmail[0]);
        
        for(int i = 0; i < thisEmails.length; i++) {
        	if(!thisEmails[i].equals(otherEmails[i])) {
        		return false;
        	}
        }
        
        return true;
    }
}