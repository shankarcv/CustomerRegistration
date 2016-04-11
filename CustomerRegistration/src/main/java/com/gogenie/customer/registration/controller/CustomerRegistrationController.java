package com.gogenie.customer.registration.controller;

import javax.inject.Inject;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.RegistrationRequest;
import com.gogenie.customer.fullregistration.model.RegistrationResponse;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;
import com.gogenie.customer.fullregistration.service.FullRegistrationService;

/**
 * Hello world!
 *
 */
@RestController
public class CustomerRegistrationController 
{
	
	@Inject
	FullRegistrationService registrationService;
	
	@RequestMapping(value="/registration", method= RequestMethod.POST)
	public RegistrationResponse customerRegistration(@RequestBody RegistrationRequest request , BindingResult result) throws CustomerRegistrationException {
		boolean isExistingCustomer = registrationService.existingCustomer(request.getEmail());
		RegistrationResponse registrationResponse = null;
		if (!isExistingCustomer) {
			registrationResponse = registrationService.registerCustomer(request);;
		} else {
			registrationResponse = new RegistrationResponse();
			registrationResponse.setRegistrationSuccess(false);
		}
		return registrationResponse;
	}
	
	@RequestMapping(value="/validate", method=RequestMethod.GET)
	public String validateExistingCustomer(@RequestParam(value="email") String emailId) throws CustomerRegistrationException {
		boolean isExistingCustomer = registrationService.existingCustomer(emailId);
		if (isExistingCustomer) {
			return "Success";
		}
		return "Not a valid user";
	}
	
	@RequestMapping(value="/retrieveSecurityQuestions", method=RequestMethod.GET)
	public SecurityQuestions retrieveSecurityQuestionsToResetPassword(@RequestParam(value="email") String emailId) throws CustomerRegistrationException {
		SecurityQuestions questions = registrationService.retrieveQuestions(emailId);
		return questions;
	}
	
	
	@RequestMapping(value="/resetPassword", method=RequestMethod.PUT)
	public String resetPassword(@RequestBody RegistrationRequest request, BindingResult result) throws CustomerRegistrationException {
		boolean passwordReset = registrationService.resetCustomerCredential(request.getEmail(), request.getPassword());
		if (passwordReset) {
			return "Password updated successfully";
		}
		return "Couldn't update this time. Please try again later";
	}
	
	@ExceptionHandler(CustomerRegistrationException.class)
	public String exceptionHandler(CustomerRegistrationException exception) {
		return "Exception " + exception.getMessage();
	}
}
