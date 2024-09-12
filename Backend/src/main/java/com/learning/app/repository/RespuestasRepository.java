package com.learning.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learning.app.entity.Foro;
import com.learning.app.entity.RespuestasForo;

@Repository
public interface RespuestasRepository extends MongoRepository<RespuestasForo, String> {

	List<RespuestasForo> findAllByForo(Foro foro);
	
	List<RespuestasForo> findByForo(Foro foro);
	
	long countByForo(Foro foro);

	RespuestasForo findFirstByForoOrderByFechaPublicacionDesc(Foro foro);

}
