package com.learning.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping
	public String login(@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "expired", required = false) String expired, 
			@RequestParam(value = "error", required = false) String error, Model model) {
		if (logout != null) {
			model.addAttribute("response", "Has cerrado sesión exitosamente");
		}
		if (expired != null) {
			model.addAttribute("response", "Su sesión ha expirado");
		}
		if (error != null) {
			model.addAttribute("response", "Usuario o Contraseña invalida");
		}
		return "login";
	}
}
