package com.learning.app.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lesson")
public class Lesson {

	@Id
	private String id;
	
	@LastModifiedDate
	private LocalDateTime lastUpdate;
	
	@CreatedDate
	private LocalDateTime createdDate;
	
	private String title;
	private String description;
	private String duration;
	private String video;
	private int order;
	private Double progress;
	private boolean completed;
	private String idCourse;
	
	public Lesson(String id, LocalDateTime lastUpdate, LocalDateTime createdDate, String title, String description,
			String duration, String video, int order, Double progress, boolean completed, String idCourse) {
		super();
		this.id = id;
		this.lastUpdate = lastUpdate;
		this.createdDate = createdDate;
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.video = video;
		this.order = order;
		this.progress = progress;
		this.completed = completed;
		this.idCourse = idCourse;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDuration() {
		return duration;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getVideo() {
		return video;
	}
	
	public void setVideo(String video) {
		this.video = video;
	}
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public Double getProgress() {
		return progress;
	}
	
	public void setProgress(Double progress) {
		this.progress = progress;
	}
	
	public boolean isCompleted() {
		return completed;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getIdCourse() {
		return idCourse;
	}

	public void setIdCourse(String idCourse) {
		this.idCourse = idCourse;
	}	
}
