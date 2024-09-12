package com.learning.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learning.app.entity.Admin;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {

	Admin findByUser(String username);

	Admin findByEmail(String email);

}
