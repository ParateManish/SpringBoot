package com.manish.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manish.controller.UserController;
import com.manish.model.FinishTask;
import com.manish.repository.IFinishTasksRepository;

@Service
public class FinishTaskDBService {

	private IFinishTasksRepository finishTasksRepo;
	private UserController userController;

	@Autowired
	public FinishTaskDBService(IFinishTasksRepository finishTasksRepo, UserController userController) {
		super();
		this.finishTasksRepo = finishTasksRepo;
		this.userController = userController;
	}

	public List<FinishTask> getAllFinishTasks() {
		List<FinishTask> list = finishTasksRepo.findAllByUserName(userController.getUsername());
		return list;
	}

	public Integer addTask(FinishTask task) {
		return finishTasksRepo.save(task).getId();
	}

	public void deleteById(Integer id) {
		finishTasksRepo.deleteById(id);
	}

	public FinishTask getTaskById(Integer id) {
		return finishTasksRepo.findById(id).get();
	}

	public void deleteChecklist(List<FinishTask> finishTaskChecklist) {
		finishTasksRepo.deleteAll(finishTaskChecklist);
	}

}
