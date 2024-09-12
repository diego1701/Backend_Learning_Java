package com.learning.app.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.app.entity.Admin;
import com.learning.app.entity.Users;
import com.learning.app.repository.AdminRepository;
import com.learning.app.repository.UsersRepository;

@RestController
@RequestMapping("/password")
public class PasswordController {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender mail;
	
	//Restablecer contraseña
	@PostMapping("/reset")
	public ResponseEntity<?> requestPassword(@RequestBody String username) {
		Users user = usersRepository.findByUser(username);
		Admin admin = adminRepository.findByUser(username);
		
		if (user == null && admin == null) {
			return ResponseEntity.badRequest().body("El usuario no ha sido encontrado");
		}
		
		String newPassword = generatePassword();
		
		if (user != null) {
			user.setPassword(passwordEncoder.encode(newPassword));
			usersRepository.save(user);
			sendEmail(user.getNombre() ,user.getEmail(), newPassword);
		} else {
			admin.setPassword(passwordEncoder.encode(newPassword));
			adminRepository.save(admin);
			sendEmail(admin.getNombre(), admin.getEmail(), newPassword);
		}
		return ResponseEntity.ok("La contraseña generada ha sido enviada al Email");
	}
	
	//Generar aleatoriamente una contraseña de 8 caracteres
	private String generatePassword() {
		return UUID.randomUUID().toString().substring(0, 8);
	}
	
	//Enviar la contraseña generada al email del usuario
	private void sendEmail(String nombre, String email, String newPassword) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("pruebastrabajos25@gmail.com");
		message.setTo(email);
		message.setSubject("Recupera tu contraseña de Learning Java!");
		message.setText("Hola " + nombre + "\n \n" + 
						"Recibimos una solicitud para restablecer la contraseña de tu cuenta. " +
						"Por esta razón hemos generado una nueva contraseña temporal para ti." 
						+ "\n \nTu nueva contraseña es: " + newPassword);
		
		mail.send(message);
	}
}
