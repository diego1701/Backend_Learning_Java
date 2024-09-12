package com.learning.app.controller;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.app.entity.Documentacion;
import com.learning.app.service.DocumentacionService;

@RestController
@RequestMapping("/documentacion")
public class DocumentacionController {

	private DocumentacionService documentacionService;
	
	public DocumentacionController(DocumentacionService documentacionService) {
		super();
		this.documentacionService = documentacionService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<byte[]> verArchivo(@PathVariable String id) {
		Documentacion archivo = documentacionService.obtenerArchivoPorId(id);
		
		if (archivo != null) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + archivo.getTitulo() + "\"")
					.header(HttpHeaders.CONTENT_TYPE, archivo.getTipo())
					.body(archivo.getContenido());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/descargar/{id}")
	public ResponseEntity<byte[]> descargarArchivo(@PathVariable String id) {
		Documentacion archivo = documentacionService.obtenerArchivoPorId(id);
		
		if (archivo != null) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + archivo.getTitulo() + "\"")
					.header(HttpHeaders.CONTENT_TYPE, archivo.getTipo())
					.body(archivo.getContenido());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/listar")
	public List<Documentacion> listarArchivos() {
		return documentacionService.listarArchivos();
	}
}
