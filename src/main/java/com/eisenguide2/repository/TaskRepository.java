package com.eisenguide2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.eisenguide2.model.Task;
import com.eisenguide2.model.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
	    List<Task> findByObjective(String objective);
	    List<Task> findAllByTitleContainingIgnoreCase(@Param("title")String Title);
	    List<Task> findByUser(User user); 
	    }
