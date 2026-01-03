package com.fitness.aiservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.service.RecommendationService;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

	@Autowired
	private RecommendationService recommendationService;
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Recommendation>> getUserRecommendation(@PathVariable String userId){
		return ResponseEntity.ok(recommendationService.getUserRecommendation(userId));
	}
	
	@GetMapping("/activity/{activityId}")
	public ResponseEntity<Recommendation> getActivityRecommendation(@PathVariable String activityId){
		return ResponseEntity.ok(recommendationService.getActivityRecommendation(activityId));
	}
	
	
}
