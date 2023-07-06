package com.Task.phoneContacts.model;

import java.util.Arrays;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "contactEmail")
public class ContactEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Email may not be null")
    @NotEmpty
    @Email
    @Pattern(regexp = ".+@.+\\..+",
            message = "Please provide a valid email address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "email_contact_id")
    private Contact contact;

    public ContactEmail() { }

    public ContactEmail(String address, Contact contact) {
        this.address = address;
        this.contact = contact;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return String.format(
                "Phone[id=%d, address='%s']",
                id, address);
    } 
    
    @Override
    public int hashCode() { 
        return address.hashCode();
    }
 
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        
        ContactEmail other = (ContactEmail) obj;
 
        return Arrays.equals(this.address.toCharArray(), other.getAddress().toCharArray());
    } 
}
