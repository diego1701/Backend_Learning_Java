package com.learning.app.configuration;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationSuccess implements AuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
		System.out.println("Autenticación exitosa para el usuario: " + auth.getName());
		String rol = auth.getAuthorities().stream()
				.map(authority -> authority.getAuthority())
				.findFirst().orElse("ROLE_USER");
		System.out.println("Rol del usuario autenticado: " + rol);
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print("{\"role\": \"" + rol + "\"}");
		out.flush();
	}  
}
