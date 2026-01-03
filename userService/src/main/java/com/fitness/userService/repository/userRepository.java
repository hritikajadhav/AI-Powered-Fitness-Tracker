package com.fitness.userService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitness.userService.models.Users;


public interface userRepository extends JpaRepository<Users,String>{

	boolean existsByEmail(String email);

	Boolean existsByKeycloakId(String userId);

	Users findByEmail(String email);

	Users findByKeycloakId(String userId);

	

}
