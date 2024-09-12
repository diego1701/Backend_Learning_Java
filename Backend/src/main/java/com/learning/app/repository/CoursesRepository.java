package com.learning.app.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learning.app.entity.Course;

@Repository
public interface CoursesRepository extends MongoRepository<Course, String> {
	
	Optional<Course> findByTitle(String title);
	
	List<Course> findTop3ByOrderByCreatedDateDesc();

}
