package com.learning.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.app.entity.Course;
import com.learning.app.entity.Lesson;
import com.learning.app.repository.CoursesRepository;
import com.learning.app.repository.LessonRepository;

@RestController
@RequestMapping("/lesson")
public class LessonController {
	
	@Autowired
	private CoursesRepository courseRepository;
	
	@Autowired
	private LessonRepository lessonRepository;
	
	@PostMapping("/create")
	public ResponseEntity<Lesson> createLesson(@RequestBody Lesson lesson) {
		try {
			Lesson savedLesson = lessonRepository.save(lesson);
			
			if (lesson.getIdCourse() != null) {
				Course course = courseRepository.findById(lesson.getIdCourse()).orElse(null);
				if (course != null) {
					List<String> lessonIds = course.getLesson();
					if (lessonIds == null) {
						lessonIds = new ArrayList<>();
					}
					lessonIds.add(savedLesson.getId());
					course.setLesson(lessonIds);
					courseRepository.save(course);
				}
			}
			
			return new ResponseEntity<>(savedLesson, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<Lesson>> getAlllessons() {
		try {
			List<Lesson> lessons = lessonRepository.findAll();
			return new ResponseEntity<>(lessons, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/select/{id}")
	public ResponseEntity<Lesson> getCourseById(@PathVariable String id) {
		Optional<Lesson> course = lessonRepository.findById(id);
		return course.map(ResponseEntity::ok)
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Lesson> updateCourse(@PathVariable String id, @RequestBody Lesson lesson) {
		if (!lessonRepository.existsById(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		lesson.setId(id);
		try {
			Lesson updatedlesson= lessonRepository.save(lesson);
			return new ResponseEntity<>(updatedlesson, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteLesson(@PathVariable String id) {
		if (!lessonRepository.existsById(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		try {
			lessonRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
