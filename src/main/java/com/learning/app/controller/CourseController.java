package com.learning.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.app.Enum.Level;
import com.learning.app.Enum.Topic;
import com.learning.app.entity.Course;
import com.learning.app.entity.Lesson;
import com.learning.app.repository.CoursesRepository;
import com.learning.app.repository.LessonRepository;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CoursesRepository courseRepository;
	

	@Autowired
	private LessonRepository lessonRepository;
	
	

	 @PostMapping("/create")
	    public ResponseEntity<String> createCourse(
	            @RequestParam("title") String title,
	            @RequestParam("description") String description,
	            @RequestParam("duration") int duration,
	            @RequestParam("level") String levelstr,
	            @RequestParam("progress") double progress,
	            @RequestParam("coverImage") MultipartFile coverImage,
	            @RequestParam("objectives") String objectivesJson,
	            @RequestParam("content") String contentJson,
	            @RequestParam("topic") String topicJson,
	            @RequestParam("creador") String creador
	    ) {

		    Optional<Course> existingCourse = courseRepository.findByTitle(title);
            if (existingCourse.isPresent()) {
                return new ResponseEntity<>("Curso con el nombre '" + title + "' ya existe.", HttpStatus.BAD_REQUEST);
            }
		 
		 
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            List<String> objectives = objectMapper.readValue(objectivesJson, new TypeReference<List<String>>() {});
	            List<String> content = objectMapper.readValue(contentJson, new TypeReference<List<String>>() {});
	            List<String> topic = objectMapper.readValue(topicJson, new TypeReference<List<String>>() {});

	            Level level = Level.valueOf(levelstr);
	            List<Topic> topicList = objectMapper.readValue(topicJson, new TypeReference<List<Topic>>() {});
	            
	           

	            
	            Course course = new Course();
	            course.setTitle(title);
	            course.setDescription(description);
	            course.setDuration(duration);
	            course.setLevel(level);
	            course.setProgress(progress);
	            course.setObjectives(objectives);
	            course.setContent(content);
	            course.setTopic(topicList);
	            course.setCreador(creador);

	            
	            if (coverImage != null && !coverImage.isEmpty()) {
	                course.setCoverImage(coverImage.getBytes());
	            }

	            Course savedCourse = courseRepository.save(course);
	            return new ResponseEntity<>("curso guardado exitosamente"+savedCourse, HttpStatus.CREATED);

	        } catch (IOException e) {
	            e.printStackTrace(); 
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	 }
	 
	 
	 @GetMapping("/list")
	    public ResponseEntity<List<Course>> getAllCourses() {
	        try {
	            List<Course> courses = courseRepository.findAll();
	            return new ResponseEntity<>(courses, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 
	 
	 
	 @GetMapping("/list/recent")
	    public ResponseEntity<List<Course>> getRecentCourses() {
	        try {
	            List<Course> courses = courseRepository.findTop3ByOrderByCreatedDateDesc();
	            return new ResponseEntity<>(courses, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	
	 

	    @GetMapping("/select/{id}")
	    public ResponseEntity<Course> getCourseById(@PathVariable String id) {
	        Optional<Course> course = courseRepository.findById(id);
	        return course.map(ResponseEntity::ok)
	                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }

	    
	    @GetMapping("/{id}/lessons")
	    public Map<String, Object> getCourseLessons(@PathVariable String id) {
	        Course course = courseRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("No existe el curso"));

	        List<Lesson> lessons = new ArrayList<>();

	        if (course.getLesson() != null && !course.getLesson().isEmpty()) {
	            List<String> lessonIds = course.getLesson();

	            List<Lesson> foundLessons = lessonRepository.findAllById(lessonIds);

	            lessons = lessonIds.stream()
	                    .map(ids -> foundLessons.stream()
	                            .filter(lesson -> lesson.getId().equals(ids))
	                            .findFirst()
	                            .orElse(null))
	                    .collect(Collectors.toList());
	        }

	        Map<String, Object> response = new HashMap<>();
	        response.put("course", course);
	        response.put("lessons", lessons);

	        return response;
	    }




	    @PostMapping("/{id}/update-lessons-order")
	    public ResponseEntity<String> updateLessonsOrder(@PathVariable String id, @RequestBody List<String> lessonIds) {
	        try {
	        	
	        	 System.out.println("Payload recibido: " + lessonIds);
	            Course course = courseRepository.findById(id)
	                    .orElseThrow(() -> new RuntimeException("No existe el curso"));
	            System.out.println("Lecciones antes de la actualización: " + course.getLesson());

	            System.out.println("ID del curso: " + id);


	            List<Lesson> lessons = lessonRepository.findAllById(lessonIds);
	            if (lessons.size() != lessonIds.size()) {
	                throw new RuntimeException("Algunas lecciones no se encontraron");
	            }


	            // Establece el nuevo orden de las lecciones en el curso
	            course.setLesson(lessonIds);
	            courseRepository.save(course);

	            // Imprime la lista de lecciones después de guardar
	            Course updatedCourse = courseRepository.findById(id).orElseThrow();
	            System.out.println("Lecciones después de guardar: " + updatedCourse.getLesson());

	            return ResponseEntity.ok("Orden de lecciones actualizado correctamente");
	        } catch (Exception e) {
	            e.printStackTrace(); // Agrega esto para depuración
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el orden de las lecciones");
	        }
	    }


	    @PutMapping("/update/{id}")
	    public ResponseEntity<String> updateCourse(
	            @PathVariable String id,
	            @RequestParam("title") String title,
	            @RequestParam("description") String description,
	            @RequestParam("duration") int duration,
	            @RequestParam("level") String levelstr,
	            @RequestParam("progress") double progress,
	            @RequestParam(value="coverImage"  ,required = false) MultipartFile coverImage,
	            @RequestParam("objectives") String objectivesJson,
	            @RequestParam("content") String contentJson,
	            @RequestParam("topic") String topicJson,
	            @RequestParam("creador") String creador
	    ) {
	        if (!courseRepository.existsById(id)) {
	            return new ResponseEntity<>("Curso no encontrado.", HttpStatus.NOT_FOUND);
	        }

	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            List<String> objectives = objectMapper.readValue(objectivesJson, new TypeReference<List<String>>() {});
	            List<String> content = objectMapper.readValue(contentJson, new TypeReference<List<String>>() {});
	            List<String> topic = objectMapper.readValue(topicJson, new TypeReference<List<String>>() {});

	            Level level = Level.valueOf(levelstr);
	            List<Topic> topicList = objectMapper.readValue(topicJson, new TypeReference<List<Topic>>() {});
	            
	            Course course = courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

	            course.setTitle(title);
	            course.setDescription(description);
	            course.setDuration(duration);
	            course.setLevel(level);
	            course.setProgress(progress);
	            course.setObjectives(objectives);
	            course.setContent(content);
	            course.setTopic(topicList);
	            course.setCreador(creador);

	            if (coverImage != null && !coverImage.isEmpty()) {
	                course.setCoverImage(coverImage.getBytes());
	            }
	            
	            

	            courseRepository.save(course);
	            return new ResponseEntity<>("Curso actualizado exitosamente.", HttpStatus.OK);

	        } catch (IOException e) {
	            e.printStackTrace();
	            return new ResponseEntity<>("Error al procesar los datos.", HttpStatus.BAD_REQUEST);
	        }
	    }

	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
	        if (!courseRepository.existsById(id)) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        try {
	            courseRepository.deleteById(id);
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 
}