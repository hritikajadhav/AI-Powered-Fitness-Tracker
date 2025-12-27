package com.fitness.activityService.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitness.activityService.model.Activity;

@Repository
public interface activityRepository extends MongoRepository<Activity,String>{

	List<Activity> findByUserId(String userId);

 
}
