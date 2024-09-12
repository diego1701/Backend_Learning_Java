package com.learning.app.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TemaForo")
public class Foro {

	@Id
	private String id;
	private String titulo;
	private String contenido;
	
	@CreatedDate
	private LocalDateTime fechaPublicacion;
	
	@DBRef
	private Users user;
	
	@DBRef
	private Admin admin;

	private long respuestasCount;
	private boolean isFixed;
	private boolean isHidden;
	
	public Foro() {
	}

	public Foro(String id, String titulo, String contenido, LocalDateTime fechaPublicacion, Users user, Admin admin,
			long respuestasCount, boolean isFixed, boolean isHidden) {
		this.id = id;
		this.titulo = titulo;
		this.contenido = contenido;
		this.fechaPublicacion = fechaPublicacion;
		this.user = user;
		this.admin = admin;
		this.respuestasCount = respuestasCount;
		this.isFixed = isFixed;
		this.isHidden = isHidden;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Users getUser() {
		return user;
	}
	
	public LocalDateTime getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
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

	public long getRespuestasCount() {
		return respuestasCount;
	}

	public void setRespuestasCount(long respuestasCount) {
		this.respuestasCount = respuestasCount;
	}

	public boolean isFixed() {
		return isFixed;
	}

	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}
}
