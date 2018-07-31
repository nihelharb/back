package com.SpringRestMongoDB.repo;


import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import com.SpringRestMongoDB.model.User;




@Repository
public interface UserRepository extends MongoRepository<User,String> {

public User findOneByUsername(String username);
}
