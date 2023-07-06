
package com.Task.phoneContacts.controller;
   
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Task.phoneContacts.service.ContactService;
import com.Task.phoneContacts.dto.*;
 
import java.util.*;


@RestController
@RequestMapping(value = "/api/contact")
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	//----------------------------
	
    @RequestMapping(value = "")
    @ResponseBody
    public String index() {
        return "Welcome to contacts.";
    } 
   
    @RequestMapping(value = "/count", method = RequestMethod.GET) 
    int countAll() {
        return contactService.getContacts().size();
    } 
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ContactDTO> list() {
    	return contactService.getContacts();
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) 
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO create(@RequestBody ContactDTO contactDto) { 
        return contactService.createContact(contactDto);
    } 
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ContactDTO get(@PathVariable("id") Long id) {
    	return contactService.getContactById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE) 
    @ResponseStatus(HttpStatus.OK)
	public ContactDTO update(@PathVariable("id") Long id, @RequestBody ContactDTO contactDto) {
		return contactService.updateContactById(id, contactDto);
	}
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE) 
    @ResponseStatus(HttpStatus.OK)
	public boolean delete(@PathVariable("id") Long id) {
		return contactService.deleteContactById(id);
	}
}