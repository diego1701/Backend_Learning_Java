package com.learning.app.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.app.entity.Admin;
import com.learning.app.entity.Comentarios;
import com.learning.app.entity.Users;
import com.learning.app.repository.AdminRepository;
import com.learning.app.repository.ComentariosRepository;
import com.learning.app.repository.UsersRepository;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private ComentariosRepository commentRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	//Guardar o actualizar la calificación de los usuarios
	@PostMapping("/score")
	public ResponseEntity<String> score(@RequestBody Comentarios comment, @AuthenticationPrincipal UserDetails userDetails) {
		Users user = usersRepository.findByUser(userDetails.getUsername());
		Admin admin = adminRepository.findByUser(userDetails.getUsername());
		
		List<Comentarios> rating;
		
		if (user != null) {
			rating = commentRepository.findByCourseAndUser(comment.getCourse(), user);
		} else if (admin != null) {
			rating = commentRepository.findByCourseAndAdmin(comment.getCourse(), admin);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no encontrado");
		}
		
		if(rating.isEmpty()) {
			if (user != null) {
				comment.setUser(user);
			} else if (admin != null) {
				comment.setAdmin(admin);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			comment.setFechaPublicacion(LocalDateTime.now());
			commentRepository.save(comment);
			return new ResponseEntity<>("La calificación y el comentario se han guardado exitosamente", HttpStatus.CREATED);
		} else {
			Comentarios comentario = rating.get(0);
			
			comentario.setStars(comment.getStars());
			comentario.setComentario(comment.getComentario());
			comentario.setFechaPublicacion(LocalDateTime.now());
			commentRepository.save(comentario);
			return new ResponseEntity<>("La calificación y el comentario se han guardado exitosamente", HttpStatus.OK);
		}
	}
	
	//Obtener el promedio de las calificaciones del curso
	@GetMapping("/view/average-score/{id}")
	public ResponseEntity<Double> getAverageScore(@PathVariable String id) {
		List<Comentarios> ratings = commentRepository.findByCourseId(id);
		
		OptionalDouble average = ratings.stream().mapToInt(Comentarios::getStars).average();
		
		if (average.isPresent()) {
			return new ResponseEntity<>(average.getAsDouble(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(0.0, HttpStatus.NO_CONTENT);
		}
	}
	
	//Obtener el comentario con mayor puntuación
	@GetMapping("/view/highest-score-comment/{id}")
	public ResponseEntity<Comentarios> getHighestScoreComment(@PathVariable String id) {
		List<Comentarios> ratings = commentRepository.findByCourseId(id);
		
		Comentarios highest = ratings.stream().max((r1, r2) -> Integer
				.compare(r1.getStars(), r2.getStars())).orElse(null);
		
		if (highest != null) {
			return new ResponseEntity<>(highest, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	//Obtener el comentario con menor puntuación
	@GetMapping("/view/lowest-score-comment/{id}")
	public ResponseEntity<Comentarios> getLowestScoreComment(@PathVariable String id) {
		List<Comentarios> ratings = commentRepository.findByCourseId(id);
		
		Comentarios lowest = ratings.stream().min((r1, r2) -> Integer
				.compare(r1.getStars(), r2.getStars())).orElse(null);
		
		if (lowest != null) {
			return new ResponseEntity<>(lowest, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	//Obtener todos los comentarios del curso
	@GetMapping("/view/{id}")
	public ResponseEntity<List<Comentarios>> getaAllComments(@PathVariable String id) {
		List<Comentarios> comments = commentRepository.findByCourseId(id);
		
		if (!comments.isEmpty()) {
			return new ResponseEntity<>(comments, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	//Eliminar comentario
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteComment(@PathVariable("id") String id) {
		Comentarios comentario = commentRepository.findById(id).orElse(null);
		
		if (comentario == null) {
			return ResponseEntity.notFound().build();
		}
		
		commentRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}