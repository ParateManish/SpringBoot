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

	public void addAllTask(List<Task> taskList) {
		taskRepo.saveAll(taskList);
	}
	
	public List<Task> getAllTasks() {
		return taskRepo.findAll();
	}

	public List<Task> getTaskByDate(Date date) {
		List<Task> list = taskRepo.findByTaskDate(date);
		return list;
	}

	public void deleteById(Integer id) {
		taskRepo.deleteById(id);
	}

	public Task getTaskById(Integer id) {
		return taskRepo.findById(id).get();
	}

}
