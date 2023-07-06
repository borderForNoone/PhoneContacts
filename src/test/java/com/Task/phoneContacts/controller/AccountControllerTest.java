package com.Task.phoneContacts.controller;
 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;

import com.Task.phoneContacts.controller.AccountController; 

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

	private static final String REGISTRATION_URL = "/api/registration";
	
	@Autowired
	private MockMvc mockMvc;
	 
	@Test
	public void contextLoads() throws Exception {
		this.mockMvc.perform(get(REGISTRATION_URL)) 
					.andDo(print())
			        .andExpect(status().isOk())
			        .andExpect(content().string(containsString("Welcome to regestration.")));
	}
	
	@Test
    public void accessDeniedTest() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(get("/api/contact"))
							                .andDo(print())
							                .andExpect(status().isUnauthorized())
							                .andReturn();
 
		assertThat(mvcResult.getResponse().getContentAsString() == "Please, login or create new account.");
    }
	 
	@Test(expected = NestedServletException.class) //because throws custom PasswordMatchException
    public void invalidRegPasswordsTest() throws Exception { 
		this.mockMvc.perform(post(REGISTRATION_URL + "/create").contentType(MediaType.APPLICATION_JSON)
		            .content("{\"login\":\"testUsername\","
		            		+ "\"password\":\"123\","
		            		+ "\"matchingPassword\":\"000\"}"))
				     .andDo(print())
					.andExpect(status().isUnauthorized()); 
    }
	 
	@Test 
    public void registrationTest() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(post(REGISTRATION_URL + "/create").contentType(MediaType.APPLICATION_JSON)
								            .content("{\"login\":\"testUsername\",\"password\":\"123\",\"matchingPassword\":\"123\"}"))
								            .andDo(print())
								            .andExpect(status().isCreated())
								            .andReturn(); 
    }
	
	@Test 
    public void loginTest() throws Exception {
		this.mockMvc.perform(post(REGISTRATION_URL + "/login").contentType(MediaType.APPLICATION_JSON)
		            .content("{\"login\":\"testUsername\",\"password\":\"123\"}"))
		            .andDo(print())
		            .andExpect(status().isOk());
    }
	
	@Test 
    public void logoutTest() throws Exception {
		this.mockMvc.perform(post(REGISTRATION_URL + "/logout").contentType(MediaType.APPLICATION_JSON)
		            .content("{\"login\":\"testUsername\"}"))
		            .andDo(print())
		            .andExpect(status().isOk());
		
		this.mockMvc.perform(get("/api/contact"))
					.andDo(print())
					.andExpect(status().isUnauthorized())
					.andReturn();
    }
}
