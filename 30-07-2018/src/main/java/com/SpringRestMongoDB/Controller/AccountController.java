package com.SpringRestMongoDB.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.SpringRestMongoDB.model.Customer;
import com.SpringRestMongoDB.model.User;
import com.SpringRestMongoDB.service.UserService;
import com.SpringRestMongoDB.util.CustomErrorType;


	@RestController
	@RequestMapping("account")
	public class AccountController {

		public static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	
		@Autowired
		private UserService userService;
		
		@CrossOrigin
		@GetMapping("/find/{username}")
		public User getusername(@PathVariable String username) {
		
			 return userService.find(username);
	 
		}
		
		
		
		
		@CrossOrigin
		@RequestMapping(value = "/register", method = RequestMethod.POST)
		public ResponseEntity<?> createUser(@RequestBody User newUser) {
			if (userService.find(newUser.getUsername()) != null) {
				logger.error("username Already exist " + newUser.getUsername());
				return new ResponseEntity(
						new CustomErrorType("user with username " + newUser.getUsername() + "already exist "),
						HttpStatus.CONFLICT);
			}
			
			
			return new ResponseEntity<User>(userService.save(newUser), HttpStatus.CREATED);
		}


		@CrossOrigin
		@RequestMapping("/login")
		public Principal user(Principal principal) {
			logger.info("user logged "+principal);
			return principal;
		}

}
