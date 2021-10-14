package com.manish.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manish.exceptions.LoginException;
import com.manish.model.Task;
import com.manish.service.DBService;

@CrossOrigin
@RestController
@RequestMapping("/restTask")
public class AlertRestController {

	private static final String REDIRECT = "redirect:";
	private static final String GET_ALL_FINISH_TASKS = "getAllFinishTasks";
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
	private static final String FINISH_TASK_LIST = "finishTaskList";
	private static final String TASK = "task";
	private static final String IS_EMPTY_LIST = "isEmptyList";
	private static final String EMPTY_STATUS = "emptyStatus";
	private static final String STATUS_LIST = "statusList";
	private static final String UNIQUE_TASK_EXCEPTION = "uniqueTaskException";
	private static final String INTERNAL_ERROR = "internalError";

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
	private static final String GET_STATUS = "/getStatus";
	private static final String ADD_STATUS_URL = "/addStatus";

	// OTHER CONSTANTS
	private static final String FINISH_STATUS_STAGE = "Finished Task";

	private DBService dbService;
	private CustomLoginController customLoginController;
	private Map<Integer, Object> taskMap = new HashMap<>();
	private UserController userController;
	private AlertController alertController;

	private String resultPage = StringUtils.EMPTY;
//	private List<FinishTask> finishTaskChecklist = new ArrayList<FinishTask>();

	@Autowired
	public AlertRestController(DBService dbService, CustomLoginController customLoginController,
			UserController userController, AlertController alertController) {
		this.dbService = dbService;
		this.customLoginController = customLoginController;
		this.userController = userController;
		this.alertController = alertController;
	}

	@GetMapping(PENDING_TASK_URL)
	public ResponseEntity<List<Task>> getAllTasks() {
		checkUserLogin();
		List<Task> list = dbService.getAllTasks(userController.getUsername(), "Pending Task");
		return new ResponseEntity<List<Task>>(list, HttpStatus.OK);
	}

	@PostMapping(path = "/abc", consumes = "application/json")
	public ResponseEntity<String> add(@RequestBody Task task) {
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	@PostMapping(SAVE_URL)
	public ResponseEntity<String> addToTaskList(@RequestBody Task task) {
		checkUserLogin();
		System.out.println("AlertController.addToTaskListd()");
		System.out.println("Task Object Data :: " + task.toString());
		String msg = StringUtils.EMPTY;

		try {
			if (StringUtils.isNotBlank(task.getTask1())) {
				Task tsk = new Task();
				tsk.setTaskDate(new Date());
				tsk.setTaskModifiedDate(new Date());
				tsk.setUserName(userController.getUsername());
				tsk.setCustomUniqueKey(task.getUserName() + "+" + task.getTask1());
				tsk.setStatusStage("Pending Task");

				Integer taskId = dbService.addTask(tsk);
				return new ResponseEntity<String>(taskId.toString(), HttpStatus.OK);
			} else {
				msg = "Internal Error,Wait for some time or Please contact Administrator";
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
		} catch (DataIntegrityViolationException e) {
			msg = "This task '" + task.getTask1().toUpperCase() + "' is already available in your task list";
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			msg = "Internal Error,Wait for some time or Please contact Administrator";
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateTask(@PathVariable("id") Integer id, @RequestBody Task task) {
		System.out.println("AlertController.editPage()");
		checkUserLogin();
		String message = null;

		Task taskDB = dbService.getTaskById(id);
		taskDB.setTaskModifiedDate(new Date());
		taskDB.setTask1(task.getTask1());

		Integer taskId = dbService.addTask(taskDB);
		message = "Task " + taskId + " is Modified";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	private void checkUserLogin() {
		System.out.println("AlertController.checkUserLogin()");
		customLoginController.checkUserLogin();
	}
	
	/*@PostMapping("/login")
	public void login() {
		customLoginController.lo
	}*/
	

}
