
package com.Task.phoneContacts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*; 
import com.Task.phoneContacts.service.AccountService;
import com.Task.phoneContacts.dto.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping(value = "/api/registration")
public class AccountController {
	 
	@Autowired
	private AccountService accountService;
	 
	@RequestMapping("")
    @ResponseBody
    public String index() {
        return "Welcome to regestration.";
    } 
     
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public boolean signup(@RequestBody AccountDTO accountDTO) { 
    	return accountService.registerNewAccount(accountDTO);
    } 
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public boolean login(@RequestBody AccountDTO accountDTO) { 
    	return accountService.logIn(accountDTO);
    } 
    
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public boolean logout(@RequestBody AccountDTO accountDTO, HttpServletRequest request, HttpServletResponse response) {
    	accountService.logOut(accountDTO);
    	
    	HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    	return true;
    } 
}