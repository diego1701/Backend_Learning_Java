package com.learning.app.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.learning.app.Dto.AdminDto;
import com.learning.app.Dto.PasswordDto;
import com.learning.app.entity.Admin;
import com.learning.app.entity.Course;
import com.learning.app.entity.CourseProgress;
import com.learning.app.entity.Documentacion;
import com.learning.app.entity.Users;
import com.learning.app.repository.AdminRepository;
import com.learning.app.repository.CoursesRepository;
import com.learning.app.repository.DocumentosRepository;
import com.learning.app.repository.UsersRepository;
import com.learning.app.service.DocumentacionService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private DocumentosRepository documentosRepository;
	
	private DocumentacionService documentacionService;
	
	public AdminController(DocumentacionService documentacionService) {
		super();
		this.documentacionService = documentacionService;
	}

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CoursesRepository courseRepository;
	
	//Obtener datos del administrador
	@GetMapping("/details")
	public AdminDto getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Admin admin = adminRepository.findByUser(username);
		
		byte[] imagen = admin.getImagenPerfil();
		String imagenBase64 = imagen != null ? Base64.getEncoder().encodeToString(imagen) : null;
		
		AdminDto adminDto = new AdminDto();
		adminDto.setNombre(admin.getNombre());
		adminDto.setApellido(admin.getApellido());
		adminDto.setFechaNacimiento(admin.getFechaNacimiento());
		adminDto.getEdad();
		adminDto.setEmail(admin.getEmail());
		adminDto.setUser(admin.getUser());
		adminDto.setImagenPerfil(imagenBase64);
		
		return adminDto;
	}
	
	//Actualizar los datos de perfil
	@PutMapping("/update-profile")
	public ResponseEntity<String> updateProfile(@RequestParam(value = "imagenPerfil", required = false) MultipartFile file,
			@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,
			@RequestParam("fechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaNacimiento,
			@RequestParam("email") String email, @RequestParam("username") String username) {
		
		try {
			Admin admin = adminRepository.findByUser(username);
			
			if (admin == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no ha sido encontrado");
			}
			
			if (adminRepository.findByEmail(email) != null && !email.equals(admin.getEmail())) {
				return ResponseEntity.badRequest().body("El correo electrónico " + email + " ya está vinculado a otra cuenta");
			}
			
			admin.setNombre(nombre);
			admin.setApellido(apellido);
			admin.setFechaNacimiento(fechaNacimiento);
			admin.setEmail(email);
			
			if (file != null && !file.isEmpty()) {
				try {
					admin.setImagenPerfil(file.getBytes());
				} catch (IOException e) {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen");
				}
			}
		
			adminRepository.save(admin);
			return ResponseEntity.ok("La información de la cuenta ha sido actualizada exitosamente");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la actualización de datos. Inténtelo de nuevo");
		}
	}
	
	//Actualizar contraseña
	@PostMapping("/update-password")
	public ResponseEntity<?> updatePassword(@RequestBody PasswordDto passwordDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Admin admin = adminRepository.findByUser(username);
		
		if (admin != null) {
			if (passwordEncoder.matches(passwordDto.getPassword(), admin.getPassword())) {
				admin.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
				adminRepository.save(admin);
				return ResponseEntity.ok("Contraseña Actualizada");
			} else {
				return ResponseEntity.badRequest().body("La contraseña actual es incorrecta");
			}
		} else {
			return ResponseEntity.badRequest().body("El usuario no ha sido encontrado");
		}
	}
	
	//Obtener todos los usuarios
	@GetMapping("/users")
	public List<Users> getAllUsers() {
		return usersRepository.findAll();
	}
	
	//DOCUMENTACION//
	
	@PostMapping("/documentacion/subir")
	public ResponseEntity<Documentacion> subirArchivo(@RequestParam("file") MultipartFile file, @RequestParam("titulo") String titulo) {
		try {
			Documentacion archivo = new Documentacion();
			archivo.setTitulo(titulo);
			archivo.setTipo(file.getContentType());
			archivo.setContenido(file.getBytes());
			
			archivo = documentosRepository.save(archivo);
			return new ResponseEntity<>(archivo, HttpStatus.CREATED);
		} catch (IOException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/documentacion/{id}")
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
	
	@GetMapping("/documentacion/descargar/{id}")
	public ResponseEntity<byte[]> descargarArchivo(@PathVariable String id) {
		Documentacion archivo = documentacionService.obtenerArchivoPorId(id);
		
		if (archivo != null) {
        	String extension = obtenerExtension(archivo.getTipo());
        	String nombreArchivo = archivo.getTitulo();
        	
        	if (!nombreArchivo.endsWith(extension)) {
        		nombreArchivo += extension;
        	}
        	
        	System.out.println("Tipo MIME: " + archivo.getTipo());
        	System.out.println("Nombre del archivo: " + nombreArchivo);
        	
        	return ResponseEntity.ok()
        			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreArchivo + "\"")
        			.header(HttpHeaders.CONTENT_TYPE, archivo.getTipo())
        			.body(archivo.getContenido());
        }
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/documentacion/listar")
	public List<Documentacion> listarArchivos() {
		System.out.println("Entrando al método listarArchivos...");
		
		List<Documentacion> archivos = documentacionService.listarArchivos();
		System.out.println("Número de archivos recuperados: " + (archivos != null ? archivos.size() : "null"));
		
		if (archivos != null) {
			for (Documentacion archivo : archivos) {
				System.out.println("Archivo: " + archivo.getTitulo() + ", ID: " + archivo.getId());
			}
		} else {
			System.out.println("La lista de archivos es nula.");
		}
		
		return archivos;
	}
	
	private String obtenerExtension(String mimeType) {
		switch (mimeType) {
			case "application/pdf":
				return ".pdf";
			case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
				return ".docx";
			case "application/msword":
				return ".doc";
			default:
				return "";
			}
	}
	
	@GetMapping("/documentacion/Search")
	public ResponseEntity<List<Documentacion>> searchDocuments(@RequestParam String titulo){
		List<Documentacion> documents = documentosRepository.findByTitulo(titulo);
		return ResponseEntity.ok(documents);
	}
	
	@PutMapping("/documentacion/edit/{id}")
	public ResponseEntity<String> updateDocumento(@PathVariable("id") String id, @RequestParam("titulo") String titulo,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		
		try {
			Documentacion documento = documentacionService.obtenerArchivoPorId(id);
			if (documento == null) {
				return new ResponseEntity<>("Documento no encontrado", HttpStatus.NOT_FOUND);
			}
			
			documento.setTitulo(titulo);
			
			// Si se ha subido un nuevo archivo, actualizar el archivo en el documento
			if (file != null && !file.isEmpty()) {
				documento.setContenido(file.getBytes());
			}
			
			documentosRepository.save(documento);
			return new ResponseEntity<>("Documento actualizado con éxito", HttpStatus.OK);
			
		} catch (IOException e) {
			return new ResponseEntity<>("Error al procesar el archivo", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al actualizar el documento", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/documentacion/get/{id}")
	public ResponseEntity<Documentacion> getDocumento(@PathVariable("id") String id) {
		try {
			Documentacion documento = documentacionService.obtenerArchivoPorId(id);
			if (documento != null) {
				return new ResponseEntity<>(documento, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/documentacion/delete/{id}")
	public ResponseEntity<String> eliminarDocumento(@PathVariable String id) {
		try {
			documentosRepository.deleteById(id);
			return ResponseEntity.ok("Documento eliminado con éxito");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el documento");
		}
	}
	
	//PROGRESS
	
	@GetMapping("/curso/{cursoId}/progreso")
	public ResponseEntity<Double> obtenerProgreso(@PathVariable String cursoId) {
		
		// Obtener el usuario autenticado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		// Obtener el usuario desde el repositorio
		Admin usuario = adminRepository.findByUser(username);
		if (usuario == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Usuario no encontrado
		}
		
		// Buscar el curso en el repositorio
		Optional<Course> cursoOpt = courseRepository.findById(cursoId);
		if (!cursoOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Curso no encontrado
		}
		
		Course curso = cursoOpt.get();
		long totalLecciones = curso.getLesson().size();
		if (totalLecciones == 0) {
			return ResponseEntity.badRequest().body(null); // No hay lecciones en el curso
		}
		
		// Buscar progreso en el curso
		Optional<CourseProgress> progresoOpt = usuario.getProgreso().stream()
				.filter(progreso -> progreso.getCursoId().equals(cursoId))
				.findFirst();
		
		if (progresoOpt.isPresent()) {
			CourseProgress progresoCurso = progresoOpt.get();
			long leccionesCompletadas = progresoCurso.getCompletedLessons().size();
			double porcentajeCompletado = (leccionesCompletadas / (double) totalLecciones) * 100;
			
			return ResponseEntity.ok(porcentajeCompletado);
		}
		
		return ResponseEntity.notFound().build(); // Progreso no encontrado para el curso
	}
	
	@PostMapping("/completados/{cursoId}/leccion/{leccionId}")
	public ResponseEntity<Void> completarLeccion(@PathVariable String cursoId, @PathVariable String leccionId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		Admin usuario = adminRepository.findByUser(username);
		if (usuario == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		if (usuario.getProgreso() == null) {
			usuario.setProgreso(new ArrayList<>());
		}
		
		Optional<CourseProgress> progresoOpt = usuario.getProgreso().stream()
				.filter(progreso -> progreso.getCursoId().equals(cursoId))
				.findFirst();
		
		if (progresoOpt.isPresent()) {
			CourseProgress progresoCurso = progresoOpt.get();
			List<String> completedLessons = progresoCurso.getCompletedLessons();
			
			if (completedLessons.contains(leccionId)) {
				completedLessons.remove(leccionId);
			} else {
				completedLessons.add(leccionId);
			}
		} else {
			CourseProgress nuevoProgreso = new CourseProgress(cursoId, List.of(leccionId));
			usuario.getProgreso().add(nuevoProgreso);
		}
		
		try {
			adminRepository.save(usuario);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/curso/{cursoId}/lecciones-completadas")
	public ResponseEntity<Set<String>> obtenerLeccionesCompletadas(@PathVariable String cursoId) {
		
		// Obtener el usuario autenticado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		// Obtener el usuario desde el repositorio
		Admin usuario =adminRepository.findByUser(username);
		if (usuario == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Usuario no encontrado
		}
		
		// Buscar el curso en el repositorio
		Optional<Course> cursoOpt = courseRepository.findById(cursoId);
		if (!cursoOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Curso no encontrado
		}
		
		// Buscar progreso en el curso
		Optional<CourseProgress> progresoOpt = usuario.getProgreso().stream()
				.filter(progreso -> progreso.getCursoId().equals(cursoId))
				.findFirst();
		
		if (progresoOpt.isPresent()) {
			CourseProgress progresoCurso = progresoOpt.get();
			Set<String> leccionesCompletadas = new HashSet<>(progresoCurso.getCompletedLessons());
			return ResponseEntity.ok(leccionesCompletadas);
		}
		
		return ResponseEntity.ok(new HashSet<>()); // No hay lecciones completadas para el curso
	}
}
