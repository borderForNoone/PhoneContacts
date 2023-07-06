package com.Task.phoneContacts.model;

import java.util.Arrays;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contactPhone")
public class ContactPhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Phone may not be null")
    @NotEmpty
    @Size(min=10, max=17)
    @Pattern(regexp = "\\d{3}-?\\d{3}-?\\d{2}-?\\d{2}|" +
            "^\\+\\d{1,2}-?\\d{3}-?\\d{3}-?\\d{2}-?\\d{2}",
            message = "Please provide a valid phone number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "phone_contact_id")
    private Contact contact;

    public ContactPhone() {}

    public ContactPhone(String number, Contact contact) {
        this.number = number;
        this.contact = contact;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String phone) {
        this.number = phone;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact phoneContact) {
        this.contact = phoneContact;
    }

    @Override
    public String toString() {
        return String.format(
                "Phone[id=%d, phone='%s']",
                id, number);
    }
    
    @Override
    public int hashCode() { 
        return number.hashCode();
    }
 
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        
        ContactPhone other = (ContactPhone) obj;
 
        return Arrays.equals(this.number.toCharArray(), other.getNumber().toCharArray()); 
    } 
}