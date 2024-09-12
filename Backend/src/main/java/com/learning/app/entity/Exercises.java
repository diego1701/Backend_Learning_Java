package com.learning.app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.learning.app.Enum.Topic;

@Document(collection = "exercises")
public class Exercises {

		@Id
		private String id;
		
		private String problem;
		
		private String answer;
		
		private Topic topic;
		


		public Exercises(String id, String problem, String answer, Topic topic) {
			super();
			this.id = id;
			this.problem = problem;
			this.answer = answer;
			this.topic = topic;
		}

		
		
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getProblem() {
			return problem;
		}

		public void setProblem(String problem) {
			this.problem = problem;
		}

		public String getAnswer() {
			return answer;
		}

		public void setAnswer(String answer) {
			this.answer = answer;
		}

		public Topic getTopic() {
			return topic;
		}

		public void setTopic(Topic topic) {
			this.topic = topic;
		}
		
		
		
		
		
}
