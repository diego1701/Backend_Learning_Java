package com.learning.app.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "respuestaAnidada")
public class RespuestaForo {

	@Id
	private String id;
	private String contenido;
	
	@CreatedDate
	private LocalDateTime fechaPublicacion;
	
	@DBRef
	private RespuestasForo respuesta;
	
	@DBRef
	private Users user;
	
	@DBRef
	private Admin admin;

	public RespuestaForo() {
	}

	public RespuestaForo(String id, String contenido, LocalDateTime fechaPublicacion, RespuestasForo respuesta,
			Users user, Admin admin) {
		this.id = id;
		this.contenido = contenido;
		this.fechaPublicacion = fechaPublicacion;
		this.respuesta = respuesta;
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

	public RespuestasForo getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(RespuestasForo respuesta) {
		this.respuesta = respuesta;
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
