package com.SpringRestMongoDB.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.SpringRestMongoDB.model.Customer;
 
public interface CustomerRepository extends MongoRepository<Customer, String>{
	List<Customer> findByAge(int age);
}