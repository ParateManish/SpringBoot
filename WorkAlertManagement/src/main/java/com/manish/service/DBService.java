package com.manish.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manish.model.Task;
import com.manish.repository.ITaskRepository;

@Service
public class DBService {
	@Autowired
	private ITaskRepository taskRepo;

	public Integer addTask(Task task) {
		return taskRepo.save(task).getId();
	}

	public List<Task> getAllTasks(){
		return taskRepo.findAll();
	}

	public List<Task> getTaskByDate(Date date){
		return taskRepo.findByTaskDate(date);
	}

}
