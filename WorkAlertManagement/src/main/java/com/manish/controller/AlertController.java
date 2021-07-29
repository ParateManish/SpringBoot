package com.manish.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String getTaskByDate(@ModelAttribute Date date, Model model) throws ParseException {
		System.out.println(date.toString());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(date);
		System.out.println("String date :: "+strDate);
		Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(strDate);  
		System.out.println("Date date :: "+date1);
		
		System.out.println("AlertController.getTaskByDate()");
		if(date1!=null) {
			System.out.println("Selected Date :: "+date1);
			final List<Task> list = dbService.getTaskByDate(date1);
			if(list.isEmpty()) {
				model.addAttribute("isListEmpty",true);
				model.addAttribute("message", "Tasks not found for the given date :: "+date1);
			}else {
				model.addAttribute("isListEmpty",false);
				model.addAttribute("list", list);
			}
		}
		return "allTaskByDate";
	}

}
