package com.learning.app.controller;

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

import com.learning.app.entity.Exercises;
import com.learning.app.repository.ExerciseRepository;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
	

    @Autowired
    private ExerciseRepository exerciseRepository;
    
    

    @PostMapping("/create")
    public ResponseEntity<Exercises> createExercise(@RequestBody Exercises exercise) {
        try {
            Exercises saved = exerciseRepository.save(exercise);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    

    @GetMapping("/list")
    public ResponseEntity<List<Exercises>> getAllExercises() {
        try {
            List<Exercises> exercises = exerciseRepository.findAll();
            return new ResponseEntity<>(exercises, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/select/{id}")
    public ResponseEntity<Exercises> getExerciseById(@PathVariable String id) {
        Optional<Exercises> exercise = exerciseRepository.findById(id);
        return exercise.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Exercises> updateExercise(@PathVariable String id, @RequestBody Exercises exercise) {
        if (!exerciseRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        exercise.setId(id);
        try {
        	Exercises updatedExercise = exerciseRepository.save(exercise);
            return new ResponseEntity<>(updatedExercise, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable String id) {
        if (!exerciseRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
        	exerciseRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}