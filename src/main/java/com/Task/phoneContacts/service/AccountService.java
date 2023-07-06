package com.Task.phoneContacts.service; 

import com.Task.phoneContacts.dto.AccountDTO;
import com.Task.phoneContacts.model.Contact; 

public interface AccountService {
	 
	boolean registerNewAccount(AccountDTO accountDto);

	boolean logIn(AccountDTO accountDto);  
	
	boolean logOut(AccountDTO accountDto);
}