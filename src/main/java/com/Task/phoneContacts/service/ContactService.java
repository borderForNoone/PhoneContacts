package com.Task.phoneContacts.service;

import com.Task.phoneContacts.dto.ContactDTO;

import java.util.List; 

public interface ContactService {
 
    ContactDTO createContact(ContactDTO contactDto);
    	  
    ContactDTO getContactById(Long id);
    
    ContactDTO updateContactById(Long id, ContactDTO contactDto); 
    
    boolean deleteContactById(Long id); 

    List<ContactDTO> getContacts();
}
