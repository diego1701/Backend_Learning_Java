package com.learning.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.learning.app.entity.Foro;

@Repository
public interface ForoRepository extends MongoRepository<Foro, String> {

	@Query(value = "{ 'isHidden': false }", sort = "{ 'isFixed': -1, 'fechaPublicacion': 1 }")
	List<Foro> findVisibleSorted();

}
