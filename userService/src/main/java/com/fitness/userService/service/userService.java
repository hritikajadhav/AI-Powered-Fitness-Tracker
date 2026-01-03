package com.fitness.userService.service;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.userService.DTO.RegisterRequest;
import com.fitness.userService.DTO.UserResponse;
import com.fitness.userService.models.Users;
import com.fitness.userService.repository.userRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class userService {
	
	@Autowired
	private userRepository repository;
	
	public UserResponse register(RegisterRequest request) {
		
		if(repository.existsByEmail(request.getEmail())) {
			
			Users existingUser = repository.findByEmail(request.getEmail());
			UserResponse userResponse = new UserResponse();
			userResponse.setId(existingUser.getId());
			userResponse.setKeycloackId(existingUser.getKeycloakId());
			userResponse.setPassword(existingUser.getPassword());
			userResponse.setEmail(existingUser.getEmail());
			userResponse.setFirstName(existingUser.getFirstName());
			userResponse.setLastName(existingUser.getLastName());
			userResponse.setCreatedAt(existingUser.getCreatedAt());
			userResponse.setUpdatedAt(existingUser.getUpdatedAt());
			return userResponse;
		}
		
		Users user = new Users();
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setKeycloakId(request.getKeycloakId());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		Users savedUser = repository.save(user);
		
		UserResponse userResponse = new UserResponse();
		userResponse.setId(savedUser.getId());
		userResponse.setKeycloackId(savedUser.getKeycloakId());
		userResponse.setPassword(savedUser.getPassword());
		userResponse.setEmail(savedUser.getEmail());
		userResponse.setFirstName(savedUser.getFirstName());
		userResponse.setLastName(savedUser.getLastName());
		userResponse.setCreatedAt(savedUser.getCreatedAt());
		userResponse.setUpdatedAt(savedUser.getUpdatedAt());
		return userResponse;
		
	}
	
	public UserResponse getUserProfile(String userId) {
		
		Users user = repository.findById(userId)
					.orElseThrow(() -> new RuntimeException("User Not Found"));
		
		UserResponse userResponse = new UserResponse();
		userResponse.setId(user.getId());
		userResponse.setKeycloackId(user.getKeycloakId());
		userResponse.setPassword(user.getPassword());
		userResponse.setEmail(user.getEmail());
		userResponse.setFirstName(user.getFirstName());
		userResponse.setLastName(user.getLastName());
		userResponse.setCreatedAt(user.getCreatedAt());
		userResponse.setUpdatedAt(user.getUpdatedAt());
		return userResponse;
	}

	public Boolean existsByUserId(String userId) {
		log.info("calling user validation API for userId{}" , userId);
		log.info("repository.keycloak{}", repository.existsByKeycloakId(userId) );
		log.info("repository.userid{}" , repository.existsById(userId));
		return repository.existsByKeycloakId(userId) || repository.existsById(userId);
	}
	
}
