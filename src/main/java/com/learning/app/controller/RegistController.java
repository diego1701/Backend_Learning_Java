package com.learning.app.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.learning.app.entity.Admin;
import com.learning.app.entity.Users;
import com.learning.app.repository.AdminRepository;
import com.learning.app.repository.UsersRepository;

@RestController
@RequestMapping("/register")
public class RegistController {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//Añadir nuevo usuario
	@PostMapping("/add-user")
	public ResponseEntity<?> addUser(@RequestParam(value = "imagenPerfil", required = false) MultipartFile file,
			@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,
			@RequestParam("fechaNacimiento") LocalDate fechaNacimiento, @RequestParam("email") String email,
			@RequestParam("user") String User, @RequestParam("password") String password,
			@RequestParam(defaultValue = "0") int cursosCompletados, @RequestParam("acceptTerms") boolean acceptTerms) {
		
		if (usersRepository.findByUser(User) != null) {
			return ResponseEntity.badRequest().body("El nombre de usuario " + User + " ya está en uso");
		}
		if (usersRepository.findByEmail(email) != null) {
			return ResponseEntity.badRequest().body("El correo electrónico " + email + " ya está vinculado a otra cuenta");
		}
		
		Users user = new Users();
		user.setNombre(nombre);
		user.setApellido(apellido);
		user.setFechaNacimiento(fechaNacimiento);
		user.setEmail(email);
		user.setUser(User);
		user.setPassword(passwordEncoder.encode(password));
		user.setCursosCompletados(cursosCompletados);
		user.setAcceptTerms(acceptTerms);
		
		try {
			if (file != null && !file.isEmpty()) {
				user.setImagenPerfil(file.getBytes());
			}
			Users savedUser = usersRepository.save(user);
			return ResponseEntity.ok(savedUser);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen");
		}
	}
	
	@GetMapping("/ver-imagen")
	public ResponseEntity<byte[]> verImagen() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		// Primero, intenta encontrar el usuario en el repositorio Users
		Users user = usersRepository.findByUser(username);
		
		// Si el usuario no se encuentra en Users, intenta encontrarlo en Admin
		if (user == null) {
			Admin admin = adminRepository.findByUser(username);
			if (admin != null) {
				byte[] imagenBytes = admin.getImagenPerfil();
				
				if (imagenBytes != null && imagenBytes.length > 0) {
					System.out.println("Tamaño de la imagen (Admin): " + imagenBytes.length);
					
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.IMAGE_JPEG);
					
					return new ResponseEntity<>(imagenBytes, headers, HttpStatus.OK);
				} else {
					System.out.println("No se encontró imagen para el administrador: " + username);
				}
			} else {
				System.out.println("Administrador no encontrado: " + username);
			}
		} else {
			byte[] imagenBytes = user.getImagenPerfil();
			
			if (imagenBytes != null && imagenBytes.length > 0) {
				System.out.println("Tamaño de la imagen (User): " + imagenBytes.length);
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.IMAGE_JPEG);
				
				return new ResponseEntity<>(imagenBytes, headers, HttpStatus.OK);
			} else {
				System.out.println("No se encontró imagen para el usuario: " + username);
			}
		}
		
		return ResponseEntity.notFound().build();
	}
}
