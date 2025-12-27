package com.fitness.activityService.service;

import com.fitness.activityService.repository.activityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.fitness.activityService.DTO.ActivityRequest;
import com.fitness.activityService.DTO.ActivityResponse;
import com.fitness.activityService.model.Activity;

@Service
@Slf4j
public class ActivityService {

	@Autowired
	private activityRepository activityRepository;
	@Autowired
	private UserValidationService userValidationService;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${rabbitmq.exchange.name}")
	private String exchange;
	
	@Value("${rabbitmq.routing.key}")
	private String routing;
	
	
	public ActivityResponse trackActivity(ActivityRequest request) {
		//System.out.println(request.getUserId());
		//System.out.println(request.getUserId().equals("694fb038-dd5c-4af6-b9a0-b4e2252e4ff3"));
		log.info("Calling uservalidation from ActivityService class");
		boolean isValid = userValidationService.validateUser(request.getUserId());
		//System.out.println(isValid + "Validity");
		if(!isValid) {
			throw new RuntimeException("Invalid User: " + request.getUserId());
		}
		
		Activity activity = Activity.builder()
				.userId(request.getUserId())
				.type(request.getType())
				.duration(request.getDuration())
				.caloriesBurnt(request.getCaloriesBurnt())
				.startTime(request.getStartTime())
				.additionalMetrics(request.getAdditionalMetrics())
				.build();
		
		Activity savedActivity = activityRepository.save(activity);
		//System.out.println("Saved" + savedActivity);
		//System.out.println("Saved ID = " + savedActivity.getId());
		//Publish to rabbitmq for AI processing
		try {
			rabbitTemplate.convertAndSend(exchange, routing, savedActivity);
		} catch(Exception e) {
			log.error("Failed to publish activity to RabbitMQ" ,e);
		}
		
		return mapToResponse(activity);
				
	}
	
	private ActivityResponse mapToResponse(Activity activity) {
		ActivityResponse response = new ActivityResponse();
		response.setId(activity.getId());
		response.setUserId(activity.getUserId());
		response.setType(activity.getType());
		response.setDuration(activity.getDuration());
		response.setCaloriesBurnt(activity.getCaloriesBurnt());
		response.setStartTime(activity.getStartTime());
		response.setAdditionalMetrics(activity.getAdditionalMetrics());
		response.setCreatedAt(activity.getCreatedAt());
		response.setUpdatedAt(activity.getUpdatedAt());
		
		return response;
		
	}

	public List<ActivityResponse> getUserActivities(String userId) {
		log.info("Getting list of activities for user" , userId);
		List<Activity> activities = activityRepository.findByUserId(userId);
		return activities.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	public ActivityResponse getUserActivitiyById(String activityId) {
		return activityRepository.findById(activityId)
				.map(this::mapToResponse)
				.orElseThrow(()-> new RuntimeException("Activity not found with id " + activityId));
	}
	

}
