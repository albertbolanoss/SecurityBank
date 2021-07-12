package com.eazybytes.springsecuritybasic.controller;

import com.eazybytes.springsecuritybasic.model.Customer;
import com.eazybytes.springsecuritybasic.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LoginController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping("/user")
	public ResponseEntity<Customer> getUserDetailsAfterLogin(Principal principal) {
		ResponseEntity responseEntity;
		try {
			Customer customer = customerService.findByEmail(principal.getName());
			responseEntity = new ResponseEntity<>(customer, HttpStatus.OK);
		} catch (UsernameNotFoundException usernameNotFoundException) {
			responseEntity = new ResponseEntity<>(usernameNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
		} catch (RuntimeException runtimeException) {
			responseEntity = new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}
}
