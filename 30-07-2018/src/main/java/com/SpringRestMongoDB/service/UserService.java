package com.SpringRestMongoDB.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.SpringRestMongoDB.model.Test;
import com.SpringRestMongoDB.model.User;
import com.SpringRestMongoDB.repo.UserRepository;




@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public User save(User user) {
		User _user = userRepository.save(new User(user.getUsername(),user.getPassword()));
		return _user;
	}

	public User update(User user) {
		return userRepository.save(user);
	}

	public User find(String username) {
		return userRepository.findOneByUsername(username);
	}

	
}
