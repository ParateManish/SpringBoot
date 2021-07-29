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
import org.springframework.web.bind.annotation.RequestParam;

import com.manish.model.FinishTask;
import com.manish.model.Task;
import com.manish.service.DBService;
import com.manish.service.FinishTaskDBService;

@Controller
@RequestMapping("/task")
public class AlertController {
	@Autowired
	private DBService dbService;
	@Autowired
	private FinishTaskDBService finishDBService;
	
	
	@RequestMapping("/home")
	public String showHome() {
		System.out.println("AlertController.showHome()");
		return "welcome";
	}

	@RequestMapping("/addNewTask")
	public String addToTaskList() {
		System.out.println("AlertController.addToTaskList()");
		return "addNewTask";
	}

	@PostMapping("/save")
	public String addToTaskListd(@ModelAttribute Task task, Model model) {
		System.out.println("AlertController.addToTaskListd()");
		task.setTaskDate(new Date());
		final Integer id = dbService.addTask(task);
		final String message = "Task " + id + " is Added in TODO List";
		model.addAttribute("message", message);
		return "addNewTask";
	}

	@GetMapping("/pendingTasks")
	public String getAllTasks(Model model) {
		final List<Task> list = dbService.getAllTasks();
		model.addAttribute("taskList", list);
		return "pendingTasks";
	}

	@GetMapping("/pendingTaskByDate")
	public String getTaskByDate() {
		return "pendingTaskByDate";
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
	
	@GetMapping("/deleteTask")
	public String deleteTask(@RequestParam("id") Integer id) {
		dbService.deleteById(id);
		return "redirect:pendingTasks";
	}

	@GetMapping("/finishTasks")
	public String finishTasks(@RequestParam("id") Integer id,Model model) {
		Task task = dbService.getTaskById(id);
		FinishTask finishTask = new FinishTask();
		finishTask.setTask1(task.getTask1());
		finishTask.setTask2(task.getTask2());
		finishTask.setTask3(task.getTask3());
		finishTask.setTask4(task.getTask4());
		finishTask.setFinishTaskDate(new Date());
		finishDBService.addTask(finishTask);
		dbService.deleteById(id);
		List<FinishTask> finishTaskList = finishDBService.getAllFinishTasks();
		model.addAttribute("finishTaskList",finishTaskList);
		return "finishTasks";
	}
	
	@GetMapping("/getAllFinishTasks")
	public String getAllFinishTasks(Model model) {
		List<FinishTask> finishTaskList = finishDBService.getAllFinishTasks();
		model.addAttribute("finishTaskList",finishTaskList);
		return "finishTasks";
	}
	
}
