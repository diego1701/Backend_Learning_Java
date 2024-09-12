package com.learning.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.app.entity.Exercises;

public interface ExerciseRepository extends MongoRepository<Exercises, String> {

}
