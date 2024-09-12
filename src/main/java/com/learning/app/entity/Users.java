package com.learning.app.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Users {

	@Id
	private String id;
	private byte[] imagenPerfil;
	private String nombre;
	private String apellido;
	private LocalDate fechaNacimiento;
	private String email;
	private String user;
	private String password;
	private int cursosCompletados;
	private boolean acceptTerms;
	
	@CreatedDate
	private LocalDateTime fechaCreado;
	
	@LastModifiedDate
	private LocalDateTime fechaActualizado;
	
	private List<CourseProgress> progreso;
	
	public Users() {
		super();
	}
	
	public Users(String id, byte[] imagenPerfil, String nombre, String apellido, LocalDate fechaNacimiento,
			String email, String user, String password, int cursosCompletados, boolean acceptTerms,
			LocalDateTime fechaCreado, LocalDateTime fechaActualizado, List<CourseProgress> progreso) {
		super();
		this.id = id;
		this.imagenPerfil = imagenPerfil;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.user = user;
		this.password = password;
		this.cursosCompletados = cursosCompletados;
		this.acceptTerms = acceptTerms;
		this.fechaCreado = fechaCreado;
		this.fechaActualizado = fechaActualizado;
		this.progreso = progreso;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public byte[] getImagenPerfil() {
		return imagenPerfil;
	}
	
	public void setImagenPerfil(byte[] imagenPerfil) {
		this.imagenPerfil = imagenPerfil;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getCursosCompletados() {
		return cursosCompletados;
	}
	
	public void setCursosCompletados(int cursosCompletados) {
		this.cursosCompletados = cursosCompletados;
	}
	
	public boolean isAcceptTerms() {
		return acceptTerms;
	}
	
	public void setAcceptTerms(boolean acceptTerms) {
		this.acceptTerms = acceptTerms;
	}

	public LocalDateTime getFechaCreado() {
		return fechaCreado;
	}

	public LocalDateTime getFechaActualizado() {
		return fechaActualizado;
	}

	public List<CourseProgress> getProgreso() {
		return progreso;
	}

	public void setProgreso(List<CourseProgress> progreso) {
		this.progreso = progreso;
	}
}
