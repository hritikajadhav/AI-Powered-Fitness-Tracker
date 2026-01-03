package com.fitness.userService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.userService.DTO.RegisterRequest;
import com.fitness.userService.DTO.UserResponse;
import com.fitness.userService.service.userService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

	@Autowired
	private userService userService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId){
		return ResponseEntity.ok(userService.getUserProfile(userId));
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
		return ResponseEntity.ok(userService.register(request));
	}
	
	@GetMapping("/{userId}/validate")
	public ResponseEntity<Boolean> validateUser(@PathVariable String userId){
		return ResponseEntity.ok(userService.existsByUserId(userId));
	}
}
