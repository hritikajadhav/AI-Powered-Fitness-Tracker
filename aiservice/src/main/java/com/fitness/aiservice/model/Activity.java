package com.fitness.aiservice.model;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;


public class Activity {
	
	private String id;
	private String userId;
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private Integer duration;
	private Integer caloriesBurnt;
	private LocalDateTime startTime;
	private Map<String,Object> additionalMetrics;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	public String getId() {
		return id;
	}
	public Activity() {
		super();
	}
	
	public Activity(String id, String userId, String type, Integer duration, Integer caloriesBurnt,
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
