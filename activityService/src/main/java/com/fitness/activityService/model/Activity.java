package com.fitness.activityService.model;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Data;

@Document(collection = "activities")
@Data
@Builder
public class Activity {
	@Id
	private String id;
	private String userId;
	private ActivityType type;
	private Integer duration;
	private Integer caloriesBurnt;
	private LocalDateTime startTime;
	@Field("metrics")
	private Map<String,Object> additionalMetrics;
	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;
	public String getId() {
		return id;
	}
	public Activity() {
		super();
	}
	public Activity(String id, String userId, ActivityType type, Integer duration, Integer caloriesBurnt,
			LocalDateTime startTime, Map<String, Object> additionalMetrics, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.type = type;
		this.duration = duration;
		this.caloriesBurnt = caloriesBurnt;
		this.startTime = startTime;
		this.additionalMetrics = additionalMetrics;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public ActivityType getType() {
		return type;
	}
	public void setType(ActivityType type) {
		this.type = type;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Integer getCaloriesBurnt() {
		return caloriesBurnt;
	}
	public void setCaloriesBurnt(Integer caloriesBurnt) {
		this.caloriesBurnt = caloriesBurnt;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public Map<String, Object> getAdditionalMetrics() {
		return additionalMetrics;
	}
	public void setAdditionalMetrics(Map<String, Object> additionalMetrics) {
		this.additionalMetrics = additionalMetrics;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
