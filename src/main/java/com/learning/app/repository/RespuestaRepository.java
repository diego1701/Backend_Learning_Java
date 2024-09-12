package com.learning.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learning.app.entity.RespuestaForo;
import com.learning.app.entity.RespuestasForo;

@Repository
public interface RespuestaRepository extends MongoRepository<RespuestaForo, String> {

	List<RespuestaForo> findByRespuesta(RespuestasForo respuestasForo);
	
}
