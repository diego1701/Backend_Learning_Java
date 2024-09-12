package com.learning.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learning.app.entity.Admin;
import com.learning.app.entity.Comentarios;
import com.learning.app.entity.Course;
import com.learning.app.entity.Users;

@Repository
public interface ComentariosRepository extends MongoRepository<Comentarios, String> {

	List<Comentarios> findByCourseAndUser(Course course, Users user);

	List<Comentarios> findByCourseId(String id);

	List<Comentarios> findByCourseAndAdmin(Course course, Admin admin);

}
