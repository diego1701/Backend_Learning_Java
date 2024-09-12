package com.learning.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.learning.app.entity.Documentacion;
import com.learning.app.repository.DocumentosRepository;

@Service
public class DocumentacionService {

	private DocumentosRepository documentosRepository;
	
	public DocumentacionService(DocumentosRepository documentosRepository) {
		this.documentosRepository = documentosRepository;
	}
	
	public Documentacion guardarArchivo(MultipartFile file, String titulo) throws IOException {
		Documentacion archivo = new Documentacion();
		archivo.setTitulo(titulo);
		archivo.setTipo(file.getContentType());
		archivo.setContenido(file.getBytes());
		
		return documentosRepository.save(archivo);
	}
	
	public Documentacion obtenerArchivoPorId(String id) {
		return documentosRepository.findById(id).orElse(null);
	}

	public List<Documentacion> listarArchivos() {
		return documentosRepository.findAll();
	}
}
