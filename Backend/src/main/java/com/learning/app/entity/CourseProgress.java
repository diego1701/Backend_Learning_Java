package com.learning.app.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courseProgress")
public class CourseProgress {

	@Id
	private String id;
	private String cursoId;
	private List<String> completedLessons;
	
	public CourseProgress() {
		super();
	}

	public CourseProgress(String id, String cursoId, List<String> completedLessons) {
		super();
		this.id = id;
		this.cursoId = cursoId;
		this.completedLessons = completedLessons;
	}
	
	public CourseProgress( String cursoId, List<String> completedLessons) {
		super();
		this.cursoId = cursoId;
		this.completedLessons = completedLessons;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public List<String> getCompletedLessons() {
		return completedLessons;
	}

	public void setCompletedLessons(List<String> completedLessons) {
		this.completedLessons = completedLessons;
	}
}
