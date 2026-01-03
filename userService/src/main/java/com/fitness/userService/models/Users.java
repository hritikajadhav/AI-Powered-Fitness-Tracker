 package com.fitness.userService.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table (name = "users")
@Data
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	private String keycloakId;
	
	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	private String firstName;
	private String lastName;
	@Enumerated(EnumType.STRING)
	private UserRole role = UserRole.USER;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
}
