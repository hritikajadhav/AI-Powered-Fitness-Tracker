package com.fitness.aiservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {
	
	
	private final GeminiService geminiService;
	
	public Recommendation generateRecommendation(Activity activity) {
		//String aiResponse=null;
		String prompt = createPromptForActivity(activity);
		//log.info(prompt);
		String aiResponse = geminiService.getAnswer(prompt);
		log.info("Response from ai {}" ,aiResponse);
		return processAiResponse(activity,aiResponse);
		
	}
	
	private Recommendation processAiResponse(Activity activity, String aiResponse) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(aiResponse);
			JsonNode textNode = rootNode.path("candidates")
					.get(0)
					.path("content")
					.path("parts")
					.get(0)
					.path("text");
			
			String jsonContent = textNode.asText()
					.replaceAll("'''json\\n","")
					.replaceAll("\\n'''", "")
					.trim();
			
			log.info("Parsed response from AI{}" , jsonContent);
			
			JsonNode analysisJson = mapper.readTree(jsonContent);
			JsonNode analysisNode = analysisJson.path("analysis");
			StringBuilder fullAnalysis = new StringBuilder();
			addAnalysisSection(fullAnalysis , analysisNode, "overall" , "Overall:");
			addAnalysisSection(fullAnalysis , analysisNode, "pace" , "Pace:");
			addAnalysisSection(fullAnalysis , analysisNode, "heartRate" , "HeartRate:");
			addAnalysisSection(fullAnalysis , analysisNode, "caloriesBurnt" , "Calories Burnt:");
			
			List<String> improvements = extractImprovements(analysisJson.path("improvements"));
			
			List<String> suggestions = extractSuggestions(analysisJson.path("suggestions"));
			
			List<String> safety = extractSafety(analysisJson.path("safety"));
			
			log.info("Inside process ai response{} activity id",activity.getId());
			 return Recommendation.builder()
					.activityId(activity.getId())
					.userId(activity.getUserId())
					.duration(activity.getDuration())
					.caloriesBurnt(activity.getCaloriesBurnt())
					.activityType(activity.getType())
					.additionalMetrics(activity.getAdditionalMetrics())
					.recommendation(fullAnalysis.toString().trim())
					.improvements(improvements)
					.suggestions(suggestions)
					.safety(safety)
					.createdAt(LocalDateTime.now())
					.build();
			
		} catch(Exception e) {
			log.info("AI Response is null{}",aiResponse);
			return createDefaultRecommendation(activity);
		}
		
	}

	private Recommendation createDefaultRecommendation(Activity activity) {
		
		return Recommendation.builder()
				.activityId(activity.getId())
				.userId(activity.getUserId())
				.activityType(activity.getType())
				.duration(activity.getDuration())
				.caloriesBurnt(activity.getCaloriesBurnt())
				.additionalMetrics(activity.getAdditionalMetrics())
				.recommendation("Unable to generate detailed analysis")
				.improvements(Collections.singletonList("Continue with your current routine."))
				.suggestions(Collections.singletonList("Consider consulting a fitness professional."))
				.safety(Arrays.asList("Stay hydrated" , "Always warm up before exercise" , "Listen to your body"))
				.createdAt(LocalDateTime.now())
				.build();
	}

	private List<String> extractSafety(JsonNode safetyNode ) {
		List<String> safety = new ArrayList<>();
		if(safetyNode.isArray()) {
			safetyNode.forEach(item -> safety.add(item.asText())
					);
		}
		return safety.isEmpty() ? 
				Collections.singletonList("No specific safety guidelines provided") : safety;
		
	}

	private List<String> extractSuggestions(JsonNode suggestionsNode) {
		List<String> suggestions = new ArrayList<>();
		if(suggestionsNode.isArray()) {
			suggestionsNode.forEach(improvement -> {
				String workout = improvement.path("workout").asText();
				String description = improvement.path("description").asText();
				suggestions.add(String.format("%s: %s", workout,description));
		
			});
		}
		return suggestions.isEmpty() ? 
				Collections.singletonList("No specific suggestions provided") : suggestions;
		
	}

	private List<String> extractImprovements(JsonNode improvementsNode) {
		List<String> improvements = new ArrayList<>();
		if(improvementsNode.isArray()) {
			improvementsNode.forEach(improvement -> {
				String area = improvement.path("area").asText();
				String detail = improvement.path("recommendation").asText();
				improvements.add(String.format("%s: %s", area,detail));
		
			});
		}
		
		return improvements.isEmpty() ? 
				Collections.singletonList("No specific improvements provided") : improvements;
		
	}

	private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
		if(!analysisNode.path(key).isMissingNode() ) {
			fullAnalysis.append(prefix)
			.append(analysisNode.path(key).asText())
			.append("\n\n");
		}
		
	}

	private String createPromptForActivity(Activity activity) {
		 return String.format("""
		 		Analyze this fitness activity and provide detailed recommendations in the following exact json format,
		 		{
		 		 "Analysis":{
		 		 	"overall":"Overall analysis here."
		 		 	"pace":"Pace analysis here."
		 		 	"heartRate:"Heartrate analysis here."
		 		 	"caloriresBurnt":"calories burnt here."
		 		 },
		 		 "improvements":[{
		 		 	"area":"Area name",
		 		 	"recommendation:"Deatiled Recommendation"		 		 
		 		 	} ],
		 		 	
		 		 	"suggestions":[{
		 		 		"workout":"Workout name",
		 		 		"description" : "Deatailed workout description"
		 		 	}],
		 		 	
		 		 	"safety":[
		 		 		"safety point 1",
		 		 		"safety point 2"
		 		 	]}
		 		 	
		 		 Analyze this activity:
		 		 Activity type: %s
		 		 Duration: %d minutes
		 		 Calories Burnt: %d
		 		 Additional Metrics: %s
		 		 
		 		 Please provide deatailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
		 		 Ensure the response format follows the exact json format shown above.	
		 		 	
		 		""", 
		 		
		 		activity.getType(),
		 		activity.getDuration(),
		 		activity.getCaloriesBurnt(),
		 		activity.getAdditionalMetrics()
				 );
	}
	
}
