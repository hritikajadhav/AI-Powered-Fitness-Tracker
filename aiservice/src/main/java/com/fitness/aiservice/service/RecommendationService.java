package com.fitness.aiservice.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.aiservice.repository.RecommendationRepository;
import com.fitness.aiservice.model.Recommendation;
@Service
public class RecommendationService {
	
	@Autowired
	private RecommendationRepository recommendationRepository;

	public List<Recommendation> getUserRecommendation(String userId) {
		return recommendationRepository.findByUserId(userId);
		
	}

	public Recommendation getActivityRecommendation(String activityId) {
		return recommendationRepository.findByActivityId(activityId)
				.orElseThrow(() -> new RuntimeException("No recommendation for this Activity ID"));
	}

}
