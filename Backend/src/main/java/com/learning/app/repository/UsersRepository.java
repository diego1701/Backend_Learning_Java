package com.learning.app.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learning.app.entity.Users;

@Repository
public interface UsersRepository extends MongoRepository<Users, String>{

	Users findByUser(String user);

	Users findByEmail(String email);

	Optional<Users> findById(ObjectId objectId);
	
}
