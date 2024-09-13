package com.learning.app.controller;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.learning.app.entity.Course;
import com.learning.app.entity.Documentacion;
import com.learning.app.repository.DocumentosRepository;
import com.learning.app.service.DocumentacionService;

@RestController
@RequestMapping("/documentacion")
public class DocumentacionController {
	
	@Autowired
	private DocumentosRepository documentacionService;
	
	
	@GetMapping("/list")
	public ResponseEntity <List<Documentacion>> listarArchivos() {
		 try {
	            List<Documentacion> docs = documentacionService.findAll();
	            return new ResponseEntity<>(docs, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	}
}
