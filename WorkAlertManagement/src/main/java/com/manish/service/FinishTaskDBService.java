package com.manish.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manish.model.FinishTask;
import com.manish.model.Task;
import com.manish.repository.IFinishTasksRepository;

@Service
public class FinishTaskDBService {
	@Autowired
	private IFinishTasksRepository finishTasksRepo;
	
	public List<FinishTask> getAllFinishTasks() {
		List<FinishTask> list = finishTasksRepo.findAll();
		return list;
	}

	public void addTask(FinishTask task) {
		finishTasksRepo.save(task);
	}
	
	public void deleteById(Integer id) {
		finishTasksRepo.deleteById(id);
	}
	
}
