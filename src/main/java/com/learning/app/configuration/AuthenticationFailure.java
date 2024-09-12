package com.learning.app.configuration;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFailure implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    	
        System.out.println("Fallo de autenticación para el usuario: " + request.getParameter("username"));
        System.out.println("Error: " + exception.getMessage());
        
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
    }
}