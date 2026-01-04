package com.fitness.aiservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ActivityMessageListener {
	@Value("${rabbitmq.queue.name}")
	private String queue;
	
	@Autowired
	private ActivityAIService aiService;
	
	@Autowired
	private RecommendationRepository recommendationRepository;
	
	@RabbitListener(queues = "activity.queue")
	public void processActivity(Activity activity) {
		log.info("Received activity for processing: {}" , activity.getId());
		log.info("Duration {}",activity.getDuration());
		log.info("Calories {}" , activity.getCaloriesBurnt());
		log.info("Generated Recommendation: {}" , aiService.generateRecommendation(activity));
		Recommendation recommendation = aiService.generateRecommendation(activity);
		recommendationRepository.save(recommendation);
	}
}
