package com.Task.phoneContacts.controller;
 
import static org.hamcrest.Matchers.containsString; 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 

import org.junit.Test;
import org.junit.runner.RunWith; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException; 
 
//TODO: change configure(WebSecurity web) to 
//		web.ignoring().antMatchers("/api/contact/**"); 
//		for execute tests

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContactErrorTest { 
	 
	private static final String CONTACT_URL = "/api/contact";
    
	@Autowired
	private MockMvc mockMvc;
	    
	@Test
	public void contextLoads() throws Exception {
		this.mockMvc.perform(get(CONTACT_URL)) 
					.andDo(print())
			        .andExpect(status().isOk())
			        .andExpect(content().string(containsString("Welcome to contacts.")));
	}
	
	@Test (expected = NestedServletException.class) // throws custom UserAlreadyExistsException
	public void cantAddExisting_Name_Contact() throws Exception {

		//-------ExistingEmail-------
		
		String contact = "{\"name\":\"alex\", "
						+ "\"emails\": [ \"alex@gmail.com\"], "
						+ "\"phones\": [ \"+380951112233\"]}";

		String contactUpdateData = "{\"name\":\"alex\", "
						    	  + "\"emails\": [], "
						    	  + "\"phones\": []}"; 
		
		createContact(contact);
		
		this.mockMvc.perform(put(CONTACT_URL + "/1").contentType(MediaType.APPLICATION_JSON)
					.content(contactUpdateData))
			        .andDo(print())
			        .andExpect(status().isOk());
		 
	}
	
	@Test (expected = NestedServletException.class) // throws custom EmailAlreadyExistsException
	public void cantAddExisting_Email_Contact() throws Exception {

		//-------ExistingEmail-------
		
		String contact = "{\"name\":\"alex\", "
						+ "\"emails\": [ \"alex@gmail.com\"], "
						+ "\"phones\": [ \"+380951112233\"]}";

		String contactUpdateData = "{\"name\":\"\", "
						    	  + "\"emails\": [\"alex@gmail.com\"], "
						    	  + "\"phones\": []}"; 
		
		createContact(contact);
		
		this.mockMvc.perform(put(CONTACT_URL + "/1").contentType(MediaType.APPLICATION_JSON)
					.content(contactUpdateData))
			        .andDo(print())
			        .andExpect(status().isOk());
	}
	
	@Test (expected = NestedServletException.class) // throws custom PhoneAlreadyExistsException
	public void cantAddExisting_Phone_Contact() throws Exception {

		//-------ExistingEmail-------
		
		String contact = "{\"name\":\"alex\", "
						+ "\"emails\": [ \"alex@gmail.com\"], "
						+ "\"phones\": [ \"+380951112233\"]}";

		String contactUpdateData = "{\"name\":\"\", "
						    	  + "\"emails\": [], "
						    	  + "\"phones\": [\"+380951112233\"]}"; 
		
		createContact(contact);
		
		this.mockMvc.perform(put(CONTACT_URL + "/1").contentType(MediaType.APPLICATION_JSON)
					.content(contactUpdateData))
			        .andDo(print())
			        .andExpect(status().isOk());
		 
	}
	
	private String createContact(String jsonContactDto) throws Exception {
		String resultContact = this.mockMvc.perform(post(CONTACT_URL + "/create").contentType(MediaType.APPLICATION_JSON)
											.content(jsonContactDto))
								            .andDo(print())
								            .andExpect(status().isCreated())
								            .andReturn().getResponse().getContentAsString();
		return resultContact;
	}  
}