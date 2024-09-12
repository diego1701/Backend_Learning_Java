package com.learning.app.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comentarios")
public class Comentarios {

	@Id
	private String id;
	private int stars;
	private String comentario;
	
	@CreatedDate
	private LocalDateTime fechaPublicacion;
	
	@DBRef
	private Course course;
	
	@DBRef
	private Users user;
	
	@DBRef
	private Admin admin;

	public Comentarios() {
	}

	public Comentarios(String id, int stars, String comentario, LocalDateTime fechaPublicacion, Course course,
			Users user, Admin admin) {
		this.id = id;
		this.stars = stars;
		this.comentario = comentario;
		this.fechaPublicacion = fechaPublicacion;
		this.course = course;
		this.user = user;
		this.admin = admin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDateTime getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
}
