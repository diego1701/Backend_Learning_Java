package com.learning.app.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.app.entity.Lesson;

public interface LessonRepository extends MongoRepository<Lesson,String> {
	
	List<Lesson> findAllById(Iterable<String> ids);

}
