package com.learning.app.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.learning.app.Enum.Level;
import com.learning.app.Enum.Topic;

@Document(collection = "course")
public class Course { 

	@Id
	private String id;
	private String title;
	private String description;
	
	@LastModifiedDate
	private LocalDateTime lastUpdate;
	
	@CreatedDate
	private LocalDateTime createdDate;
	
	private int duration;
	private Level level;
	private List<String> objectives;
	private List<String> content;
	private double progress;
	private List<Topic> topic;
	private byte[] coverImage;
	private List<String> lesson;
	private String creador;
	
	public Course() {
		super();
	}

	public Course(String id, String title, String description, LocalDateTime lastUpdate, LocalDateTime createdDate,
			int duration, Level level, List<String> objectives, List<String> content, double progress,
			List<Topic> topic, byte[] coverImage, List<String> lesson) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.lastUpdate = lastUpdate;
		this.createdDate = createdDate;
		this.duration = duration;
		this.level = level;
		this.objectives = objectives;
		this.content = content;
		this.progress = progress;
		this.topic = topic;
		this.coverImage = coverImage;
		this.lesson = lesson;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}

	public List<String> getObjectives() {
		return objectives;
	}

	public void setObjectives(List<String> objectives) {
		this.objectives = objectives;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public LocalDateTime getCreated() {
		return createdDate;
	}

	public List<String> getContent() {
		return content;
	}

	public void setContent(List<String> content) {
		this.content = content;
	}

	public List<Topic> getTopic() {
		return topic;
	}

	public void setTopic(List<Topic> topic) {
		this.topic = topic;
	}

	public byte[] getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(byte[] coverImage) {
		this.coverImage = coverImage;
	}

	public List<String> getLesson() {
		return lesson;
	}

	public void setLesson(List<String> lesson) {
		this.lesson = lesson;
	}



	public String getCreador() {
		return creador;
	}



	public void setCreador(String creador) {
		this.creador = creador;
	}

	
}
