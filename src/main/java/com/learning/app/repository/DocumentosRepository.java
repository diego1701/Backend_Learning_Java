package com.learning.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learning.app.entity.Documentacion;

@Repository
public interface DocumentosRepository extends MongoRepository<Documentacion, String> {
	
	List<Documentacion> findByTitulo(String titulo);

}
