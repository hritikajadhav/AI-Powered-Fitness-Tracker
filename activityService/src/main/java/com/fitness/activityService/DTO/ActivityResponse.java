package com.fitness.activityService.DTO;

import java.time.LocalDateTime;
import java.util.Map;

import com.fitness.activityService.model.ActivityType;

import lombok.Data;
@Data
public class ActivityResponse {
	
	private String id;
	private String userId;
	private ActivityType type;
	private Integer duration;
	private Integer caloriesBurnt;
	private LocalDateTime startTime;
	private Map<String,Object> additionalMetrics;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	
	public String getId() {
		return id;
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
