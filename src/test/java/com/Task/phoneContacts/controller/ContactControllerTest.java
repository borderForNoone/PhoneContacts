package com.Task.phoneContacts.controller;
 
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith; 
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType; 
import org.springframework.test.context.junit4.SpringRunner; 
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.Task.phoneContacts.ContactTestUtil; 

//TODO: change configure(WebSecurity web) to 
//      web.ignoring().antMatchers("/api/contact/**"); 
//      for execute test

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest { 
	 
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
	
	@Test
	public void checkCRUDContactByMostWorstAndEasiestWay() throws UnsupportedEncodingException, Exception {
		//-------Add--------
		
		String expectedContact = ContactTestUtil.ContactsJson[0];
		String actualContact = createContact(ContactTestUtil.ContactsJson[0]);
		
		JSONAssert.assertEquals(expectedContact, actualContact, false);
		//get
		String findCotnactById = this.mockMvc.perform(get(CONTACT_URL + "/1"))
											  .andDo(print())
											  .andReturn().getResponse().getContentAsString();
		JSONAssert.assertEquals(expectedContact, findCotnactById, false); 
		
		//-------Update-------
		
		String contact = "{\"name\":\"alex\", "
						+ "\"emails\": [ \"alex@gmail.com \"], "
						+ "\"phones\": [ \"+380951112233\"]}";

		String contactUpdateData = "{\"name\":\"\", "
						    	  + "\"emails\": [\"added@gmail.com\"], "
						    	  + "\"phones\": [\"0950006655\"]}";

		String contactUpdated = "{\"name\":\"alex\", "
					    	  + "\"emails\": [\"alex@gmail.com\", \"added@gmail.com\"], "
					    	  + "\"phones\": [\"+380951112233\", \"0950006655\"]}";
		
		createContact(contact);
		
		String contactActual = this.mockMvc.perform(put(CONTACT_URL + "/2").contentType(MediaType.APPLICATION_JSON)
									.content(contactUpdateData))
							        .andDo(print())
							        .andExpect(status().isOk())
							        .andReturn().getResponse().getContentAsString();
		
		JSONAssert.assertEquals(contactUpdated, contactActual, false);  
		
		//-----Delete------
		 
		createContact(ContactTestUtil.ContactsJson[1]);  
		
		assertThat(getContactCount()).isEqualTo(3);  
		
		this.mockMvc.perform(delete(CONTACT_URL + "/1"))
			        .andDo(print())
			        .andExpect(status().isOk());
		 
		assertThat(getContactCount()).isEqualTo(2);  
	}
	
	private String createContact(String jsonContactDto) throws Exception {
		String resultContact = this.mockMvc.perform(post(CONTACT_URL + "/create").contentType(MediaType.APPLICATION_JSON)
											.content(jsonContactDto))
								            .andDo(print())
								            .andExpect(status().isCreated())
								            .andReturn().getResponse().getContentAsString();
		return resultContact;
	} 
	
	private int getContactCount() throws UnsupportedEncodingException, Exception {
		String countContacts= this.mockMvc.perform(get(CONTACT_URL + "/count"))
										  .andDo(print())
										  .andReturn().getResponse().getContentAsString();
		return Integer.parseInt(countContacts);
	} 
}
