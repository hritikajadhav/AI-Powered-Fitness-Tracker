package com.fitness.activityService.DTO;

import java.time.LocalDateTime;
import java.util.Map;

import com.fitness.activityService.model.ActivityType;

import lombok.Data;

@Data
public class ActivityRequest {
	private String userId;
	private ActivityType type;
	private Integer duration;
	private Integer caloriesBurnt;
	private LocalDateTime startTime;
	private Map<String,Object> additionalMetrics;
	
}
