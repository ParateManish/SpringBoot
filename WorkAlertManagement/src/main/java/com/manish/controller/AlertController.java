package com.manish.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.manish.model.DateAndStatusTaskPropagator;
import com.manish.model.FinishTask;
import com.manish.model.Task;
import com.manish.service.DBService;
import com.manish.service.FinishTaskDBService;

@Controller
@RequestMapping("/task")
public class AlertController {

	private static final String REDIRECT = "redirect:";
	private static final String GET_ALL_FINISH_TASKS = "getAllFinishTasks";
	private static final String FINISH_TASK_CHECKLIST = "finishTasksChecklist";
	private static final String WELCOME = "welcome";
	private static final String ADD_NEW_TASK = "addNewTask";
	private static final String PENDING_TASKS = "pendingTasks";
	private static final String PENDING_TASK_BY_DATE = "pendingTaskByDate";
	private static final String ALL_TASK_BY_DATE = "allTaskByDate";
	private static final String FINISH_TASK = "finishTasks";
	private static final String EDIT_PAGE = "editPage";
	private static final String LOGIN_PAGE = "loginPage";
	private static final String MESSAGE = "message";
	private static final String STATUS_PAGE = "statusPage";

	// BELOW ARE CONSTATNTS FOR MODEL ATTRIBUTE
	private static final String ERR_MESSAGE = "errMessage";
	private static final String TASKLIST = "taskList";
	private static final String LIST = "list";
	private static final String LOGIN_ERR_MSG = "loginErrMsg";
	private static final String FINISH_TASK_MODEL = "finishTask";
	private static final String FINISH_TASK_LIST = "finishTaskList";
	private static final String TASK = "task";
	private static final String IS_EMPTY_LIST = "isEmptyList";

	// BELOW ARE THE CONSTANTS FOR THE URL
	private static final String HOME_URL = "/home";
	private static final String ADD_NEW_TASK_URL = "/addNewTask";
	private static final String SAVE_URL = "/save";
	private static final String PENDING_TASK_URL = "/pendingTasks";
	private static final String PENDING_TASK_BY_DATE_URL = "/pendingTaskByDate";
	private static final String DELETE_TASK_URL = "/deleteTask";
	private static final String DELETE_FINISH_TASK_URL = "/deleteFinishTask";
	private static final String FINISH_TASK_URL = "/finishTasks";
	private static final String GET_ALL_FINISH_TASKS_URL = "/getAllFinishTasks";
	private static final String EDIT_PAGE_URL = "/editPage";
	private static final String EDIT_URL = "/edit";
	private static final String PROPAGATE_URL = "/propagate";
	private static final String EDIT_FINISH_PAGE_URL = "/editFinishPage";
	private static final String EDIT_FINISH_TASK_URL = "/editFinishTask";
	private static final String SINGLE_FINISH_TASK_URL = "/singleFinishTasks";
	private static final String DELETE_FINISH_TASK_CHECKLIST_URL = "/deleteFinishTaskChecklist";
	private static final String ADD_TO_CHECKLIST_URL = "/addToChecklist";
	private static final String GET_FINISH_TASK_CHECKLIST = "/getFinishTaskChecklist";
	private static final String GET_STATUS = "/getStatus";
	private static final String ADD_STATUS_URL = "/addStatus";
	
	private static final String EMPTY_STATUS = "emptyStatus";
	private static final String STATUS_LIST = "statusList";

	private DBService dbService;
	private FinishTaskDBService finishDBService;
	private CustomLoginController customLoginController;
	private Map<Integer, Object> taskMap = new HashMap<>();
	private UserController userController;

	private String resultPage = StringUtils.EMPTY;
	private List<FinishTask> finishTaskChecklist = new ArrayList<FinishTask>();

	@Autowired
	public AlertController(DBService dbService, FinishTaskDBService finishDBService,
			CustomLoginController customLoginController,UserController userController) {
		this.dbService = dbService;
		this.finishDBService = finishDBService;
		this.customLoginController = customLoginController;
		this.userController = userController;
	}

	@GetMapping(HOME_URL)
	public String showHome() {
		System.out.println("AlertController.showHome()");
		return WELCOME;
	}

	@GetMapping(ADD_NEW_TASK_URL)
	public String addToTaskList(Model model) {
		System.out.println("AlertController.addToTaskList()");
		resultPage = checkUserLogin(model);
		if (!StringUtils.isBlank(resultPage)) {
			return resultPage;
		}
		return ADD_NEW_TASK;

	}

	@PostMapping(SAVE_URL)
	public String addToTaskList(@ModelAttribute Task task, Model model) {
		System.out.println("AlertController.addToTaskListd()");
		System.out.println("Task Object Data :: " + task.toString());
		if (StringUtils.isBlank(task.getTask1()) && StringUtils.isBlank(task.getTask2())
				&& StringUtils.isBlank(task.getTask3()) && StringUtils.isBlank(task.getTask4())) {
			model.addAttribute(ERR_MESSAGE, "Sorry Task is not Added,Please mention at Least 1 task in above");
			return ADD_NEW_TASK;

		}
		
		List<Task> taskList = new ArrayList<Task>();
		List<String> taskStrList = new ArrayList<String>();
		if(StringUtils.isNotBlank(task.getTask1()))
			taskStrList.add(task.getTask1());
		if(StringUtils.isNotBlank(task.getTask2()))
			taskStrList.add(task.getTask2());
		if(StringUtils.isNotBlank(task.getTask3()))
			taskStrList.add(task.getTask3());
		if(StringUtils.isNotBlank(task.getTask4()))
			taskStrList.add(task.getTask4());
		
		for(String taskName : taskStrList) {
			Task tsk = new Task();
			tsk.setTask1(taskName);
			tsk.setTaskDate(new Date());
			tsk.setTaskModifiedDate(new Date());
			tsk.setUserName(userController.getUsername());
			tsk.setCustomUniqueKey(tsk.getUserName()+"+"+tsk.getTask1());
			taskList.add(tsk);
		}
		
//		task.setTaskDate(new Date());
//		task.setTaskModifiedDate(new Date());
//		final Integer id = dbService.addTask(task);
		dbService.addAllTask(taskList);
		String message = StringUtils.EMPTY;
		if(taskList.size()==1) 
			message = "Task is Added in TODO List";
		else
			message = "Tasks are Added in TODO List";
			
		model.addAttribute(MESSAGE, message);
		return ADD_NEW_TASK;
	}

	@GetMapping(PENDING_TASK_URL)
	public String getAllTasks(Model model) {
		System.out.println("AlertController.getAllTasks()");

		resultPage = checkUserLogin(model);
		if (!StringUtils.isBlank(resultPage)) {
			return resultPage;
		}

		checkUserLogin(model);
		Boolean isEmptyList = false;
		final List<Task> list = dbService.getAllTasks(userController.getUsername());
		if (list.size() == 0) {
			System.out.println("list is empty");
			isEmptyList = true;
			model.addAttribute(MESSAGE, "Congratulations! You Don't have pending task now");
		} else {
			isEmptyList = false;
			System.out.println("list is not empty");
			model.addAttribute(TASKLIST, list);
		}
		model.addAttribute(IS_EMPTY_LIST, isEmptyList);
		return PENDING_TASKS;
	}

	@GetMapping(PENDING_TASK_BY_DATE_URL)
	public String getTaskByDate() {
		System.out.println("AlertController.getTaskByDate()");
		return PENDING_TASK_BY_DATE;
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
				model.addAttribute(MESSAGE, "Tasks not found for the given date :: " + date1);
			} else {
				model.addAttribute("isListEmpty", false);
				model.addAttribute(LIST, list);
			}
		}
		return ALL_TASK_BY_DATE;
	}

	@GetMapping(DELETE_TASK_URL)
	public String deleteTask(@RequestParam("id") Integer id) {
		System.out.println("AlertController.deleteTask()");
		dbService.deleteById(id);
		return REDIRECT + PENDING_TASKS;
	}

	@GetMapping(DELETE_FINISH_TASK_URL)
	public String deleteFinishTask(@RequestParam("id") Integer id) {
		System.out.println("AlertController.deleteFinishTask()");
		System.out.println("Record is deleted with id " + id);
		finishDBService.deleteById(id);
		return REDIRECT + GET_ALL_FINISH_TASKS;
	}

	@GetMapping(FINISH_TASK_URL)
	public String finishTasks(@RequestParam("id") Integer id, Model model) throws IllegalAccessException, InvocationTargetException {
		System.out.println("AlertController.finishTasks()");

		resultPage = checkUserLogin(model);
		if (!StringUtils.isBlank(resultPage)) {
			return resultPage;
		}

		checkUserLogin(model);
		String message = "Record with id " + id + " is deleted";
		Task task = dbService.getTaskById(id);
		FinishTask finishTask = new FinishTask();

		BeanUtils.copyProperties(finishTask,task);
		
		finishTask.setFinishTaskDate(new Date());
		finishDBService.addTask(finishTask);
		dbService.deleteById(id);
		List<FinishTask> finishTaskList = finishDBService.getAllFinishTasks();
		model.addAttribute(FINISH_TASK_LIST, finishTaskList);
		model.addAttribute(MESSAGE, message);
		return REDIRECT + GET_ALL_FINISH_TASKS;
	}

	@GetMapping(GET_ALL_FINISH_TASKS_URL)
	public String getAllFinishTasks(Model model) {
		System.out.println("AlertController.getAllFinishTasks()");

		resultPage = checkUserLogin(model);
		if (!StringUtils.isBlank(resultPage)) {
			return resultPage;
		}

		checkUserLogin(model);
		List<FinishTask> finishTaskList = finishDBService.getAllFinishTasks();
		model.addAttribute(FINISH_TASK_LIST, finishTaskList);
		return FINISH_TASK;
	}

	@GetMapping(EDIT_PAGE_URL)
	public String getEditPage(@RequestParam("id") Integer id, Model model) {
		System.out.println("AlertController.getEditPage()-START");
		System.out.println("edit id :: " + id);
		Task task = dbService.getTaskById(id);
		System.out.println("task Data :: " + task.toString());
		model.addAttribute(TASK, task);
		taskMap.put(id, task);
		System.out.println("AlertController.getEditPage()-END");
		return EDIT_PAGE;
	}

	@PostMapping(EDIT_URL)
	public String editPage(@ModelAttribute Task task, Model model) {
		System.out.println("AlertController.editPage()");
		if (taskMap.get(task.getId()) != null) {
			Task taskObj = (Task) taskMap.get(task.getId());

			System.out.println(task.toString());
			task.setTaskDate(taskObj.getTaskDate());
			task.setTaskModifiedDate(new Date());
			task.setStatus(taskObj.getStatus());
			System.out.println("Edited Task Data :: " + task.toString());

			final Integer id = dbService.addTask(task);
			final String message = "Task " + id + " is Modified in TODO List";
			model.addAttribute(MESSAGE, message);
			return EDIT_PAGE;
		} else
			return EDIT_PAGE;
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

	@GetMapping(PROPAGATE_URL)
	public String propagateModelObjToUI(Model model) {
		System.out.println("AlertController.propagateModelObjToUI()-START");
		model.addAttribute(LOGIN_ERR_MSG, "Please Login");
		return LOGIN_PAGE;
	}

	@GetMapping(EDIT_FINISH_PAGE_URL)
	public String getEditFinishPage(@RequestParam("id") Integer id, Model model) {
		System.out.println("AlertController.getEditPage()-START");
		System.out.println("edit id :: " + id);
		FinishTask finishTask = finishDBService.getTaskById(id);
		System.out.println("finishTask Data :: " + finishTask.toString());
		model.addAttribute(FINISH_TASK_MODEL, finishTask);
		taskMap.put(id, finishTask);
		System.out.println("AlertController.getEditPage()-END");
		return EDIT_PAGE;
	}

	@PostMapping(EDIT_FINISH_TASK_URL)
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
			model.addAttribute(MESSAGE, message);
			return EDIT_PAGE;
		} else
			return EDIT_PAGE;
	}

	@GetMapping(SINGLE_FINISH_TASK_URL)
	public String singleFinishTasks(@RequestParam("id") Integer id, Model model) throws IllegalAccessException, InvocationTargetException {
		System.out.println("AlertController.finishTasks()");

		resultPage = checkUserLogin(model);
		if (!StringUtils.isBlank(resultPage)) {
			return resultPage;
		}

		checkUserLogin(model);
		String message = "Record with id " + id + " is deleted";
		Task task = dbService.getTaskById(id);
		FinishTask finishTask = new FinishTask();
		
		BeanUtils.copyProperties(finishTask, task);

		finishTask.setFinishTaskDate(new Date());
		finishDBService.addTask(finishTask);
		dbService.deleteById(id);
		List<FinishTask> finishTaskList = finishDBService.getAllFinishTasks();
		model.addAttribute(FINISH_TASK_LIST, finishTaskList);
		model.addAttribute(MESSAGE, message);

		return REDIRECT + PENDING_TASKS;
	}

	@GetMapping(DELETE_FINISH_TASK_CHECKLIST_URL)
	public String deleteSelectedFinishTask() {
		finishDBService.deleteChecklist(finishTaskChecklist);
		return FINISH_TASK_CHECKLIST;
	}

	@GetMapping(GET_FINISH_TASK_CHECKLIST)
	public String getCheckList(Model model) {
		model.addAttribute(FINISH_TASK_CHECKLIST,finishTaskChecklist);
		return FINISH_TASK_CHECKLIST;
	}
	
	@GetMapping(ADD_TO_CHECKLIST_URL)
	public String addToChecklist(@RequestParam("id") Integer id, Model model) {
		FinishTask task = finishDBService.getTaskById(id);
		finishTaskChecklist.add(task);
		model.addAttribute(FINISH_TASK_CHECKLIST, finishTaskChecklist);
		return REDIRECT + GET_ALL_FINISH_TASKS;
	}
	
	@GetMapping(GET_STATUS)
	public String getStatus(@RequestParam("id") Integer id,Model model) {
		filterStatusAndTask(id, model,false);
		return STATUS_PAGE;
	}

	private void filterStatusAndTask(Integer id, Model model,Boolean isAddingStatus) {
		String finalStatus = StringUtils.EMPTY;
		Task taskDB = dbService.getTaskById(id);
		System.out.println("task Data :: "+taskDB.toString());
		List<DateAndStatusTaskPropagator> list = new ArrayList<DateAndStatusTaskPropagator>();
		String status = taskDB.getStatus();

		if(StringUtils.isBlank(status) && !isAddingStatus) {
			model.addAttribute(EMPTY_STATUS,"You Don't have Status for this task");
		}else if(status.contains("#")) {
			for (String retval: status.split("#")) {
				DateAndStatusTaskPropagator propagator = new DateAndStatusTaskPropagator();
				String[] split = retval.split("_");
				int length = split.length;
				for(int i = 0 ; i<length;i++) {
					String statusSplit = split[i];
					System.out.println(statusSplit);
					if(statusSplit.endsWith("Status|")) {
						finalStatus = statusSplit.replace("|Status|", "");
						System.out.println(finalStatus);
						propagator.setStatus(finalStatus);
					}else {
						propagator.setDate(statusSplit);
					}
				}
				if(StringUtils.isNotBlank(finalStatus)) {
					list.add(propagator);
				}
		      }
			model.addAttribute(STATUS_LIST,list);
		} /*else if(status.contains("_")) {
				DateAndStatusTaskPropagator propagator = new DateAndStatusTaskPropagator();
				String[] split = status.split("_");
				int length = split.length;
				for(int i = 0 ; i<length;i++) {
					String statusSplit = split[i];
					System.out.println(statusSplit);
					if(statusSplit.endsWith("Status|")) {
						finalStatus = statusSplit.replace("|Status|", "");
						System.out.println(finalStatus);
						propagator.setStatus(finalStatus);
					}else {
						propagator.setDate(statusSplit);
					}
				}
				if(StringUtils.isNotBlank(finalStatus)) {
					list.add(propagator);
				}
			model.addAttribute("statusList",list);
			}else {
			model.addAttribute("status",status);
		}*/
		model.addAttribute("task",taskDB);
	}
	
	@PostMapping(ADD_STATUS_URL)
	public String addStatus(@ModelAttribute Task task,Model model) {
		Task taskDB = dbService.addStatus(task,false);
		Boolean isAddingStatus = true;
		filterStatusAndTask(taskDB.getId(), model,isAddingStatus );
		return STATUS_PAGE;
	}
	
	@RequestMapping(value = "/deleteStatus/{status}/{id}" )
	public String removeTaskStatus(	
									@PathVariable(value = "status") String status,
									@PathVariable(value = "id" , required = true) Integer id ,
									Model model
									) {
		Task taskDB = dbService.getTaskById(id);
		String statusDB = taskDB.getStatus();

		if (statusDB.contains("#")) {
			String concat = StringUtils.EMPTY;
			for (String retval : statusDB.split("#")) {
				if (StringUtils.isNotBlank(retval)) {
					retval = "#" + retval;
					if (retval.startsWith("#" + status + "|Status|")) {
						System.out.println(retval);
						System.err.println(retval+" is deleted");
						retval = StringUtils.EMPTY;
					}
					concat = concat + retval;
				}
			}
			System.out.println(concat);
			taskDB.setStatus(concat);
			dbService.addStatus(taskDB,true);
		}
		return getStatus(taskDB.getId(), model);
	}
}
