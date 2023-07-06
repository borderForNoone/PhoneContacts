package com.Task.phoneContacts.service.impl;
 
import java.util.Set;

import com.Task.phoneContacts.model.*;
import com.Task.phoneContacts.dao.AccountDao;
import com.Task.phoneContacts.dao.ContactDao;
import com.Task.phoneContacts.dto.ContactDTO;
import com.Task.phoneContacts.error.*;
import com.Task.phoneContacts.service.ContactService;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
 
import java.util.*;

@Service
public class ContactServiceImpl implements ContactService {
  
    @Autowired
    private ContactDao contactDao;
 
    @Override
    public ContactDTO createContact(ContactDTO contactDto) {  
    	if(!isNameUnique(contactDto.getName())) {
    		throw new ContactAlreadyExistsException(contactDto.getName());
    	} 
    	
    	Contact createdContact = convertToEntity(contactDto);
    	createdContact = contactDao.save(createdContact);
    	
        return convertToDto(createdContact); 
    } 
    
	@Override
	public ContactDTO getContactById(Long id) {
		Optional<Contact> contact = contactDao.findById(id); 
		
		if (!contact.isPresent()) {
			throw new ContactNotFoundException(id);
		} 
		
		return convertToDto(contact.get());
	}
 
	@Override
	public ContactDTO updateContactById(Long id, ContactDTO contactDto) { 
		if(!isNameUnique(contactDto.getName())) {
			throw new ContactAlreadyExistsException(contactDto.getName());
		}

		Contact contact = contactDao.findById(id).get();
		 
		if(!isEmailUnique(contact.getContactEmails(), contactDto.getEmails())) {
			throw new EmailAlreadyExistsException();
		} 
		if(!isPhoneUnique(contact.getContactPhones(), contactDto.getPhones())) {
			throw new PhoneAlreadyExistsException();
		}
		 
		updateContactProperties(contactDto, contact);
		 
		Contact updatedContact = contactDao.save(contact);
		
		return convertToDto(updatedContact);
	}  
 
    @Override
    public List<ContactDTO> getContacts() {
    	List<Contact> contacts = contactDao.findAll();
    	
    	List<ContactDTO> contatcsDto = new ArrayList<>();
    	for(Contact c : contacts)
    		contatcsDto.add(convertToDto(c));
    	
        return contatcsDto;
    }
     
	@Override
	public boolean deleteContactById(Long id) {
		Contact contact = contactDao.findById(id).get();
		contactDao.deleteById(id);
		return true;
	} 
    
    private Contact convertToEntity(ContactDTO contactDTO) {
    	Contact contact = new Contact(contactDTO.getName());  
    	Set<ContactEmail> contactEmails = new HashSet<>();
    	Set<ContactPhone> contactPhones = new HashSet<>();
    	
    	for(String address : contactDTO.getEmails()) {
    		contactEmails.add(new ContactEmail(address, contact));
    	} 
    	for(String number : contactDTO.getPhones()) {
    		contactPhones.add(new ContactPhone(number, contact));
    	} 
    	
    	contact.setContactEmails(contactEmails);
    	contact.setContactPhones(contactPhones);
    	
        return contact;
    }
     
    private ContactDTO convertToDto(Contact contact) {
        ContactDTO contactDto = new ContactDTO();  
        contactDto.setName(contact.getName());
         
        String contactEmails[] = new String[contact.getContactEmails().size()]; 
        int i = 0;
        for(ContactEmail e: contact.getContactEmails()) {
        	contactEmails[i++] = e.getAddress();
        }
        String contactPhones[] = new String[contact.getContactPhones().size()]; 
        int j = 0;
        for(ContactPhone e: contact.getContactPhones()) {
        	contactPhones[j++] = e.getNumber();
        }
        
        contactDto.setEmails(contactEmails);
        contactDto.setPhones(contactPhones);
        
        return contactDto;
    }

    private boolean isNameUnique(String name) {
        return findByContactName(name) == null;
    }  
      
    private boolean isEmailUnique(Set<ContactEmail> oldEmails, String... newEmails) { 
    	ContactEmail[] oldEmailsArr = oldEmails.toArray(new ContactEmail[0]);
    	List<String> newEmailsList = Arrays.asList(newEmails);
    	
    	for(int i = 0; i < oldEmailsArr.length; i++) {
    		if(newEmailsList.contains(oldEmailsArr[i].getAddress())) {
    			return false;
    		}
    	}
    	return true;
    } 
    
    private boolean isPhoneUnique(Set<ContactPhone> oldPhones, String... newPhones) { 
    	ContactPhone[] oldPhonesArr = oldPhones.toArray(new ContactPhone[0]);
    	List<String> newPhonesList = Arrays.asList(newPhones);
 
    	for(int i = 0; i < oldPhonesArr.length; i++) { 
    		if(newPhonesList.contains(oldPhonesArr[i].getNumber())) { 
    			return false;
    		}
    	}
    	return true;
    } 
    
    private void updateContactProperties(ContactDTO newContact, Contact oldContact) {
    	if(newContact.getName() != "") {
    		oldContact.setName(newContact.getName());
    	}
    	
    	if(newContact.getEmails().length != 0) {
    		Set<ContactEmail> contactEmails = oldContact.getContactEmails(); 
    		String[] newEmails = newContact.getEmails();
    		for(int i = 0; i < newEmails.length; i++) {
    			contactEmails.add(new ContactEmail(newEmails[i], oldContact));
    		}
    		oldContact.setContactEmails(contactEmails);
    	}
    	
    	if(newContact.getPhones().length != 0) {
    		Set<ContactPhone> contactPhones = oldContact.getContactPhones(); 
    		String[] newPhones = newContact.getPhones();
    		for(int i = 0; i < newPhones.length; i++) {
    			contactPhones.add(new ContactPhone(newPhones[i], oldContact));
    		}
    		oldContact.setContactPhones(contactPhones);
    	}
    }
     
    private Contact findByContactName(String name) {
        return contactDao.findByName(name);
    }
}
