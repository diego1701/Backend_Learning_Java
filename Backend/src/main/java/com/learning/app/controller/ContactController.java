package com.learning.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.app.Dto.ContactDto;

@RestController
@RequestMapping("/contact")
public class ContactController {
	
	@Autowired
	private JavaMailSender mail;
	
	//Enviar mensaje del usuario al correo predeterminado
	@PostMapping
	public ResponseEntity<?> contactUs(@RequestBody ContactDto contactDto) {
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom(contactDto.getEmail());
		email.setTo("pruebastrabajos25@gmail.com");
		email.setSubject("Contáctanos en Learning Java - " + contactDto.getNombre());
		email.setText("Remitente: " + contactDto.getNombre() + "\n" + 
						"\nEmail: " + contactDto.getEmail() + "\n" +
						"\nMensaje: " + contactDto.getContenido());
		
		mail.send(email);
		
		return ResponseEntity.ok("Mensaje enviado");
	}
}
