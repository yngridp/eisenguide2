package com.eisenguide2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eisenguide2.model.Task;
import com.eisenguide2.model.User;
import com.eisenguide2.repository.TaskRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskService {

       @Autowired
       private TaskRepository taskRepository;
       
	    public List<Task> findAll() {
	        return taskRepository.findAll();
	    }

	    public Task findById(Long id) {
	        return taskRepository.findById(id).orElseThrow();
	    }

	    public Task save(Task task) {
	        return taskRepository.save(task);
	    }

	    public void deleteById(Long id) {
	        taskRepository.deleteById(id);
	    }
	    public List<Task> getTasksByObjective(String objective) {
	        return taskRepository.findByObjective(objective);
	    }
	    public List<Task> searchTasks(String title) {
	        return taskRepository.findAllByTitleContainingIgnoreCase(title);
	    }
	    public List<Task> findByUser(User user) {
	        return taskRepository.findByUser(user);
	    }

}