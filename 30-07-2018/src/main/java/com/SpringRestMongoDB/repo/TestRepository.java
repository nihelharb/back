package com.SpringRestMongoDB.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.SpringRestMongoDB.model.Customer;
import com.SpringRestMongoDB.model.Test;

public interface TestRepository extends MongoRepository<Test, String>{
	List<Test> findByURL(String URL);
	Test findByid(String id);

//	Object loadUserByUsername(String username);

}
