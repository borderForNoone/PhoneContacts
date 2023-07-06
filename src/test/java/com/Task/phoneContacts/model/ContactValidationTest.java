package com.Task.phoneContacts.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
 
import org.junit.Before;
import org.junit.Test; 

public class ContactValidationTest {
	 
	private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    public void incorrectEmailFailValidation() { 

        ContactEmail contactEmail = new ContactEmail();
        
        contactEmail.setAddress("myAddress"); 
        Set<ConstraintViolation<ContactEmail>> violations = validator.validate(contactEmail); 
        assertFalse(violations.isEmpty());
        
        contactEmail.setAddress("myAdr@mail"); 
        violations = validator.validate(contactEmail); 
        assertFalse(violations.isEmpty());
        
        contactEmail.setAddress("my2mail.com"); 
        violations = validator.validate(contactEmail); 
        assertFalse(violations.isEmpty());
    } 
    @Test
    public void correctEmailPassValidation() { 

        ContactEmail contactEmail = new ContactEmail();
        contactEmail.setAddress("address420@mail.com"); 
        Set<ConstraintViolation<ContactEmail>> violations = validator.validate(contactEmail);
        assertTrue(violations.isEmpty());
        
        contactEmail.setAddress("address420@mail.com.ua"); 
        violations = validator.validate(contactEmail); 
        assertTrue(violations.isEmpty());
    }
    
    @Test
    public void incorrectPhoneFailValidation() { 

        ContactPhone contactPhone = new ContactPhone();
        contactPhone.setNumber("095-111-22-333"); 
        Set<ConstraintViolation<ContactPhone>> violations = validator.validate(contactPhone);
        assertFalse(violations.isEmpty());
        
        contactPhone.setNumber("380951112233"); 
        violations = validator.validate(contactPhone);
        assertFalse(violations.isEmpty());
        
        contactPhone.setNumber("095111223"); 
        violations = validator.validate(contactPhone);
        assertFalse(violations.isEmpty());
         
    } 
     
    @Test
    public void correctPhoneFailValidation() { 

    	 ContactPhone contactPhone = new ContactPhone();
         contactPhone.setNumber("+380951112233"); 
         Set<ConstraintViolation<ContactPhone>> violations = validator.validate(contactPhone);
         assertTrue(violations.isEmpty());
         
         contactPhone.setNumber("095-111-22-33"); 
         violations = validator.validate(contactPhone);
         assertTrue(violations.isEmpty());
         
         contactPhone.setNumber("+38-095-111-22-33"); 
         violations = validator.validate(contactPhone);
         assertTrue(violations.isEmpty());
         
         contactPhone.setNumber("0951112233"); 
         violations = validator.validate(contactPhone);
         assertTrue(violations.isEmpty());
    }
}
