package com.learning.app.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "documentos")
public class Documentacion {

	@Id
	private String id;
	private String titulo;
	private String tipo;
	private byte[] contenido;
	
	@CreatedDate
	private LocalDateTime fechaSubida;

	public Documentacion() {
		super();
	}
	
	public Documentacion(String id, String titulo, String tipo, byte[] contenido) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.tipo = tipo;
		this.contenido = contenido;
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
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public byte[] getContenido() {
		return contenido;
	}
	
	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
	
	public LocalDateTime getFechaSubida() {
		return fechaSubida;
	}
}
