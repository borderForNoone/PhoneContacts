package com.Task.phoneContacts.dao;

import org.springframework.data.repository.CrudRepository;

import com.Task.phoneContacts.model.Account;

public interface AccountDao extends CrudRepository<Account, Long> {
	
    Account findByLogin(String name); 
}
