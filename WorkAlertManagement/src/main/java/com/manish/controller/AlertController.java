package com.manish.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.Thymeleaf;

import com.manish.model.Task;
import com.manish.service.DBService;

@Controller
@RequestMapping("/task")
public class AlertController {
	@Autowired
	private DBService dbService;

	@RequestMapping("/home")
	public String showHome() {
		System.out.println("AlertController.showHome()");
		return "welcome";
	}

	@RequestMapping("/addToTask")
	public String addToTaskList() {
		System.out.println("AlertController.addToTaskList()");
		return "registerTask";
	}

	@PostMapping("/save")
	public String addToTaskListd(@ModelAttribute Task task, Model model) {
		System.out.println("AlertController.addToTaskListd()");
		task.setTaskDate(new Date());
		final Integer id = dbService.addTask(task);
		final String message = "Task " + id + " is Added in TODO List";
		model.addAttribute("message", message);
		return "registerTask";
	}

	@GetMapping("/allTasks")
	public String getAllTasks(Model model) {
		final List<Task> list = dbService.getAllTasks();
		model.addAttribute("taskList", list);
		return "allTasks";
	}

	@GetMapping("/taskByDate")
	public String getTaskByDate() {
		return "taskByDate";
	}

	@PostMapping("/allTaskByDate")
	public String getTaskByDate(@ModelAttribute Task task, Model model) {
		System.out.println("AlertController.getTaskByDate()");
		if(task.getTaskDate()!=null) {
			System.out.println("Selected Date :: "+task.getTaskDate());
			final List<Task> list = dbService.getTaskByDate(task.getTaskDate());
			model.addAttribute("list", list);
		}
		return "allTaskByDate";
	}

}
