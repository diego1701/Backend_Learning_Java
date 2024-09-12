package com.learning.app.Dto;

import java.time.LocalDate;
import java.time.Period;

public class AdminDto {
	
	private String imagenPerfil;
	private String nombre;
	private String apellido;
	private LocalDate fechaNacimiento;
	private int edad;
	private String email;
	private String user;
	
	public String getImagenPerfil() {
		return imagenPerfil;
	}
	
	public void setImagenPerfil(String imagenPerfil) {
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

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
		
	public void setFechaNacimiento(LocalDate dateOfBirth) {
		this.fechaNacimiento = dateOfBirth;
		this.edad = calculateAge(dateOfBirth);
	}
	
	public int getEdad() {
		return edad;
	}
	
	private int calculateAge(LocalDate birthDate) {
		if (birthDate != null) {
			return Period.between(birthDate, LocalDate.now()).getYears();
		} else {
			return 0;
		}
	}
}
