package com.learning.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learning.app.entity.AuthenticationRequest;
import com.learning.app.entity.AuthenticationResponse;

@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping("/auth")
	private ResponseEntity<?> authentication(@RequestBody AuthenticationRequest authRequest){
		String user = authRequest.getUser();
		String password = authRequest.getPassword();
		try {
			Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user, password));
			SecurityContextHolder.getContext().setAuthentication(auth);
			
			String rol = auth.getAuthorities().stream()
					.map(authority -> authority.getAuthority())
					.findFirst().orElse("ROLE_USER");
			
			if ("ROLE_ADMIN".equals(rol)) {
				return ResponseEntity.ok(new AuthenticationResponse("Autenticación Exitosa - Admin ", rol , user ));
			} else {
				return ResponseEntity.ok(new AuthenticationResponse("Autenticación Exitosa ",rol, user ));
			}
		} catch (Exception e) {
			return ResponseEntity.ok(new AuthenticationResponse("Error durante la autenticación del usuario"));
		}
	}
}
