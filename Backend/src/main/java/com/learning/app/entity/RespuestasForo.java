package com.learning.app.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "respuestasForo")
public class RespuestasForo {

	@Id
	private String id;
	private String contenido;
	
	@CreatedDate
	private LocalDateTime fechaPublicacion;
	
	@DBRef
	private Foro foro;
	
	@DBRef
	private Users user;
	
	@DBRef
	private Admin admin;

	public RespuestasForo() {
	}

	public RespuestasForo(String id, String contenido, LocalDateTime fechaPublicacion, Foro foro, Users user, Admin admin) {
		this.id = id;
		this.contenido = contenido;
		this.fechaPublicacion = fechaPublicacion;
		this.foro = foro;
		this.user = user;
		this.admin = admin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public LocalDateTime getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Foro getForo() {
		return foro;
	}

	public void setForo(Foro foro) {
		this.foro = foro;
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
