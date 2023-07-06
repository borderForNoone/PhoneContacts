package com.Task.phoneContacts.service.impl;
 
import com.Task.phoneContacts.model.*;
import com.Task.phoneContacts.dto.ContactDTO;
import com.Task.phoneContacts.dao.AccountDao;
import com.Task.phoneContacts.dao.ContactDao;
import com.Task.phoneContacts.dto.AccountDTO;
import com.Task.phoneContacts.error.*;
import com.Task.phoneContacts.service.ContactService;
import com.Task.phoneContacts.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
 
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
	
	private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
	
	@Autowired
    public AccountServiceImpl(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
       this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }
	
	@Autowired
	private AccountDao accountDao;
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    } 
	 
	@Override
	public boolean registerNewAccount(AccountDTO accountDto) {
	    if (isLoginExist(accountDto.getLogin())) {
	        throw new UserAlreadyExistsException(accountDto.getLogin());
	    }
	    
	    if(!isStringEquals(accountDto.getPassword(), accountDto.getMatchingPassword())) {
	    	throw new PasswordMatchException();
	    }
	     
	    String accountLogin = accountDto.getLogin();
	    String encrytedPassword = this.passwordEncoder().encode(accountDto.getPassword()); 
    	
    	Account account = new Account();
    	account.setLogin(accountLogin); 
    	account.setPassword(encrytedPassword); 
	    accountDao.save(account); 
	    
	    saveAccountToLocalStorage(accountLogin, encrytedPassword);
	    
	    return true;
	}
	
	public boolean logIn(AccountDTO accountDto) {
		Account account = accountDao.findByLogin(accountDto.getLogin());
		
		if(account != null) {
			String encrytedPassword = this.passwordEncoder().encode(accountDto.getPassword()); 
			if(isStringEquals(account.getPassword(), encrytedPassword)) {
				String accountLogin = accountDto.getLogin();
			     
			    boolean success = saveAccountToLocalStorage(accountLogin, encrytedPassword);
			    return success;
			} else { 
				return false;
			}
		} 
		return false;
	}
	
	public boolean logOut(AccountDTO accountDto) {
		String accountLogin = accountDto.getLogin();
		Account account = accountDao.findByLogin(accountLogin);
		
		if(account != null) {  
			boolean success = removeAccountFromLocalStorage(accountLogin);
			 
			return success;
		} else {
			return false;
		}
	}
	 
	private boolean isLoginExist(String userlogin) {
		return accountDao.findByLogin(userlogin) != null;
	} 
	
	private boolean saveAccountToLocalStorage(String accountLogin, String encrytedPassword) {
		UserDetails currentUser = User.withUsername(accountLogin).password(encrytedPassword).roles("USER").build();
		if(!inMemoryUserDetailsManager.userExists(accountLogin)) {
			inMemoryUserDetailsManager.createUser(currentUser);
		}
	    return true;
	}
	
	private boolean removeAccountFromLocalStorage(String accountLogin) {
		if( inMemoryUserDetailsManager.userExists(accountLogin)) {
			inMemoryUserDetailsManager.deleteUser(accountLogin); 
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isStringEquals(String s1, String s2) {
		return Arrays.equals(s1.toCharArray(), s2.toCharArray());
	} 
}
