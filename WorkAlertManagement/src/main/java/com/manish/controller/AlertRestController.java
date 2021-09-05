/*package com.manish.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manish.model.FinishTask;
import com.manish.model.Task;
import com.manish.service.DBService;
import com.manish.service.FinishTaskDBService;

@RestController
@RequestMapping("/restTask")
public class AlertRestController {
	@Autowired
	private DBService dbService;
	@Autowired
	private FinishTaskDBService finishDBService;

	@Autowired
	private CustomLoginController customLoginController;

	private Map<Integer, Object> taskMap = new HashMap<>();

	String resultPage = StringUtils.EMPTY;

	@RequestMapping("/home")
	public String showHome() {
		System.out.println("AlertController.showHome()");
		return "welcome";
	}

	@RequestMapping("/addNewTask")
	public String addToTaskList(Model model) {
   		System.out.println("AlertController.addToTaskList()");
		resultPage = checkUserLogin(model);
		if (!StringUtils.isBlank(resultPage)) {
			return resultPage;
		}
		return "addNewTask";

	}

	@PostMapping("/save")
	public String addToTaskList(@ModelAttribute Task task, Model model) {
		System.out.println("AlertController.addToTaskListd()");
		System.out.println("Task Object Data :: " + task.toString());
		if (StringUtils.isBlank(task.getTask1()) && StringUtils.isBlank(task.getTask2())
				&& StringUtils.isBlank(task.getTask3()) && StringUtils.isBlank(task.getTask4())) {
			model.addAttribute("errMessage", "Sorry Task is not Added,Please mention at Least 1 task in above");
			return "addNewTask";

		}
		task.setTaskDate(new Date());
		task.setTaskModifiedDate(new Date());
		final Integer id = dbService.addTask(task);
		final String message = "Task " + id + " is Added in TODO List";
		model.addAttribute("message", message);
		return "addNewTask";
	}

	@GetMapping("/pendingTasks")
	public String getAllTasks(Model model) {
		System.out.println("AlertController.getAllTasks()");

		resultPage = checkUserLogin(model);
		if (!StringUtils.isBlank(resultPage)) {
			return resultPage;
		}

		checkUserLogin(model);
		Boolean isEmptyList = false;
		final List<Task> list = dbService.getAllTasks();
		if (list.size() == 0) {
			System.out.println("list is empty");
			isEmptyList = true;
			model.addAttribute("message", "Congratulations! You Don't have pending task now");
		} else {
			isEmptyList = false;
			System.out.println("list is not empty");
			model.addAttribute("taskList", list);
		}
		model.addAttribute("isEmptyList", isEmptyList);
		return "pendingTasks";
	}

	@GetMapping("/pendingTaskByDate")
	public String getTaskByDate() {
		System.out.println("AlertController.getTaskByDate()");
		return "pendingTaskByDate";
	}

	@PostMapping("/allTaskByDate")
	public String getTaskByDate(@ModelAttribute Date date, Model model) throws ParseException {
		System.out.println("AlertController.getTaskByDate()");

		resultPage = checkUserLogin(model);
		if (!StringUtils.isBlank(resultPage)) {
			return resultPage;
		}

		checkUserLogin(model);
		System.out.println(date.toString());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(date);
		System.out.println("String date :: " + strDate);
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
		System.out.println("Date date :: " + date1);

		System.out.println("AlertController.getTaskByDate()");
		if (date1 != null) {
			System.out.println("Selected Date :: " + date1);
			final List<Task> list = dbService.getTaskByDate(date1);
			if (list.isEmpty()) {
				model.addAttribute("isListEmpty", true);
				model.addAttribute("message", "Tasks not found for the given date :: " + date1);
			} else {
				model.addAttribute("isListEmpty", false);
				model.addAttribute("list", list);
			}
		}
		return "allTaskByDate";
	}

	@GetMapping("/deleteTask")
	public String deleteTask(@RequestParam("id") Integer id) {
		System.out.println("AlertController.deleteTask()");
		dbService.deleteById(id);
		return "redirect:pendingTasks";
	}

	@GetMapping("/deleteFinishTask")
	public String deleteFinishTask(@RequestParam("id") Integer id) {
		System.out.println("AlertController.deleteFinishTask()");
		System.out.println("Record is deleted with id " + id);
		finishDBService.deleteById(id);
		return "redirect:getAllFinishTasks";
	}

	@GetMapping("/finishTasks")
	public String finishTasks(@RequestParam("id") Integer id, Model model) {
		System.out.println("AlertController.finishTasks()");

		resultPage = checkUserLogin(model);
		if (!StringUtils.isBlank(resultPage)) {
			return resultPage;
		}

		checkUserLogin(model);
		String message = "Record with id " + id + " is deleted";
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
		model.addAttribute("finishTaskList", finishTaskList);
		model.addAttribute("message", message);
		return "redirect:pendingTasks";
	}

	@GetMapping("/getAllFinishTasks")
	public List<FinishTask> getAllFinishTasks(Model model) {
		System.out.println("AlertController.getAllFinishTasks()");
		List<FinishTask> finishTaskList = new ArrayList<FinishTask>();

		resultPage = checkUserLogin(model);
		if (!StringUtils.isBlank(resultPage)) {
			finishTaskList = null;
			return finishTaskList;
		}

		checkUserLogin(model);
		finishTaskList = finishDBService.getAllFinishTasks();
		model.addAttribute("finishTaskList", finishTaskList);
//		return "finishTasks";
		return finishTaskList;
	}

	@GetMapping("/editPage")
	public String getEditPage(@RequestParam("id") Integer id, Model model) {
		System.out.println("AlertController.getEditPage()-START");
		System.out.println("edit id :: " + id);
		Task task = dbService.getTaskById(id);
		System.out.println("task Data :: "+task.toString());
		model.addAttribute("task", task);
		taskMap.put(id, task);
		System.out.println("AlertController.getEditPage()-END");
		return "editPage";
	}

	@PostMapping("/edit")
	public String editPage(@ModelAttribute Task task, Model model) {
		System.out.println("AlertController.editPage()");
		if (taskMap.get(task.getId()) != null) {
			Task taskObj = (Task) taskMap.get(task.getId());

			System.out.println(task.toString());
			task.setTaskDate(taskObj.getTaskDate());
			task.setTaskModifiedDate(new Date());
			System.out.println("Edited Task Data :: " + task.toString());

			final Integer id = dbService.addTask(task);
			final String message = "Task " + id + " is Modified in TODO List";
			model.addAttribute("message", message);
			return "editPage";
		} else
			return "editPage";
	}

	public String checkUserLogin(Model model) {
		System.out.println("AlertController.checkUserLogin()");
		try {
			customLoginController.checkUserLogin();
			return null;
		} catch (Exception e) {
//			model.addAttribute("loginErrMsg","Please Login" );
			String resultPage = propagateModelObjToUI(model);
			System.out.println("AlertController.addToTaskList()-Exception " + e.getMessage());
			return resultPage;
		}
	}

	@GetMapping("/propagate")
	public String propagateModelObjToUI(Model model) {
		System.out.println("AlertController.propagateModelObjToUI()-START");
		model.addAttribute("loginErrMsg", "Please Login");
		return "loginPage";
	}

	@GetMapping("/editFinishPage")
	public String getEditFinishPage(@RequestParam("id") Integer id, Model model) {
		System.out.println("AlertController.getEditPage()-START");
		System.out.println("edit id :: " + id);
		FinishTask finishTask = finishDBService.getTaskById(id);
		System.out.println("finishTask Data :: "+finishTask.toString());
		model.addAttribute("finishTask", finishTask);
		taskMap.put(id, finishTask);
		System.out.println("AlertController.getEditPage()-END");
		return "editPage";
	}
	
	@PostMapping("/editFinishTask")
	public String editFinishPage(@ModelAttribute FinishTask task, Model model) {
		System.out.println("AlertController.editPage()");
		if (taskMap.get(task.getId()) != null) {
			FinishTask taskObj = (FinishTask) taskMap.get(task.getId());

			System.out.println(task.toString());
			task.setFinishTaskDate(task.getFinishTaskDate());
			task.setTaskModifiedDate(new Date());
			System.out.println("Edited Task Data :: " + task.toString());

			final Integer id = finishDBService.addTask(task);
			final String message = "Task " + id + " is Modified in Finish Task List";
			model.addAttribute("message", message);
			return "editPage";
		} else
			return "editPage";
	}
	
	@GetMapping("/singleFinishTasks")
	public String singleFinishTasks(@RequestParam("id") Integer id, Model model) {
		System.out.println("AlertController.finishTasks()");

		resultPage = checkUserLogin(model);
		if (!StringUtils.isBlank(resultPage)) {
			return resultPage;
		}

		checkUserLogin(model);
		String message = "Record with id " + id + " is deleted";
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
		model.addAttribute("finishTaskList", finishTaskList);
		model.addAttribute("message", message);
		
		return "redirect:pendingTasks";
	}
}
*/