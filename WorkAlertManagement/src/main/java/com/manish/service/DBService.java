package com.manish.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

	public void addAllTask(List<Task> taskList) throws Exception{
		taskRepo.saveAll(taskList);
	}
	
	public List<Task> getAllTasks(String username, String statusStage) {
		return taskRepo.findAllByUserNameAndStatusStage(username,statusStage);
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

	public Task addStatus(Task task,Boolean isDeleting) {
		String status = StringUtils.EMPTY;
		if(!isDeleting) {
			Task taskDB = getTaskById(task.getId());
			taskDB.setTaskStatusDate(new Date());
			if(StringUtils.isBlank(taskDB.getStatus()))
				status = "#"+task.getStatus()+"|Status|_"+taskDB.getTaskStatusDate();
			else	
				status = taskDB.getStatus()+"#"+task.getStatus()+"|Status|_"+taskDB.getTaskStatusDate();
			taskDB.setStatus(status);
			taskRepo.save(taskDB);
			System.out.println("Task Status is Added");
			return taskDB;
		}else {
			taskRepo.save(task);
			System.out.println("Task Status is Added");
			return task;
		}
	}

	public List<Task> getAllFinishedTasks(String finishStatusStage) {
		List<Task> list = taskRepo.findAllByStatusStage(finishStatusStage);
		return list;
	}

}
