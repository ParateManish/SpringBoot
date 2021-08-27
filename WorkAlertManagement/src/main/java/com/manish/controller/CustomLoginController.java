package com.manish.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manish.model.UserLogin;
import com.manish.repository.IUserLoginRepository;
import com.manish.service.UserLoginDBService;
import com.manish.service.UserWishMessageService;

@Controller
@RequestMapping("/customUserLogin")
public class CustomLoginController {

	// BELOW ARE THE CONSTANTS FOR THE URL
	private static final String HOME_URL = "/home";
	private static final String GET_SINGUP_PAGE_URL = "/getSignupPage";
	private static final String SAVE_USER_URL = "/saveUser";
	private static final String GET_LOGIN_PAGE_URL = "/getLoginPage";
	private static final String CUSTOM_LOGIN_URL = "/customLogin";
	private static final String CUSTOM_LOGOUT_URL = "/customLogout";
	private static final String FORGET_URL = "/forget";

	// BELOW ARE THE CONSTANTS FOR THE HTML PAGE
	private static final String HOME_PAGE = "home";
	private static final String SINGUP_PAGE = "signUpPage";
	private static final String LOGIN_PAGE = "loginPage";
	private static final String WELCOME_PAGE = "welcome";
	private static final String EMAIL_FORGET_PAGE = "emailForgetpage";

	// BELOW ARE THE CONSTANTS FOR THE MODEL ATTRIBUTE
	private static final String MESSAGE = "message";
	private static final String WISH_MSG = "wishMsg";
	private static final String USERNAME = "username";
	private static final String USER_NOT_LOGIN_MSG = "userNotLoginMsg";
	private static final String LOGOUT_MSG = "logoutMsg";
	private static final Object LOGIN_REQUEST_MSG = "Please Login";

	private UserLoginDBService dbService;
	private IUserLoginRepository userRepo;
	private UserWishMessageService wmService;

	public Map<String, String> loginMap = new HashMap<String, String>();

	@Autowired
	public CustomLoginController(UserLoginDBService dbService, IUserLoginRepository userRepo,
			UserWishMessageService wmService) {
		this.dbService = dbService;
		this.userRepo = userRepo;
		this.wmService = wmService;
	}

	@GetMapping(HOME_URL)
	public String getHomePage() {
		return HOME_PAGE;
	}

	@GetMapping(GET_SINGUP_PAGE_URL)
	public String getSignUpPage() {
		return SINGUP_PAGE;
	}

	@PostMapping(SAVE_USER_URL)
	public String registerUser(@ModelAttribute UserLogin user, Model model) {
		dbService.saveUser(user);
		String message = "Hello " + user.getUserName() + " You are Successfully Registerd";
		model.addAttribute(MESSAGE, message);
		return HOME_PAGE;
	}

	@GetMapping(GET_LOGIN_PAGE_URL)
	public String getLoginPage() {
		return LOGIN_PAGE;
	}

	/*	@PostMapping("/login")
			public String checkUser(@ModelAttribute UserLogin user, Model model) {
				Map<String, String> map = dbService.getUser(user.getUserName(), user.getPassword());
		
				if (map.get(MESSAGE) != null) {
					model.addAttribute(MESSAGE, map.get(MESSAGE));
					return LOGIN_PAGE;
				} else
					return WELCOME_PAGE;
			}
	*/

	@PostMapping(CUSTOM_LOGIN_URL)
	public String checkUserLogin(@ModelAttribute UserLogin user, Model model) {
		System.out.println("LoginController.checkUser()");

		UserLogin userDB = userRepo.findByUserName(user.getUserName());
		if (userDB == null) {
			System.err.println("user not found");
			throw new UsernameNotFoundException("User not found");
		} else {
			String userWishMessage = getUserWishMessage();
			model.addAttribute(WISH_MSG, userWishMessage);
			model.addAttribute(USERNAME, userDB.getUserName());
			System.out.println("user is found :: " + userDB.toString());

		}

		if (!StringUtils.isBlank(user.getUserName())) {
			Map<String, String> dbUser = dbService.getUser(user.getUserName(), user.getPassword());
			loginMap.put(USERNAME, user.getUserName());
		} else {
			System.out.println("User is not logged In");
			model.addAttribute(USER_NOT_LOGIN_MSG, LOGIN_REQUEST_MSG);
		}
		return WELCOME_PAGE;
	}

	public void checkUserLogin() throws Exception {
		System.out.println("CustomLoginController.checkUserLogin()-START");
		String username = StringUtils.EMPTY;
		if (loginMap.get(USERNAME) != null) {
			username = loginMap.get(USERNAME).toString();
		}
		System.out.println("Username :: " + username);
		if (StringUtils.isBlank(username) || username == null) {
			System.err.println(LOGIN_REQUEST_MSG);
			throw new Exception("Please Login");
		}
		System.out.println("CustomLoginController.checkUserLogin()-END");
	}

	@GetMapping(CUSTOM_LOGOUT_URL)
	public String logout(Model model) {
		System.out.println("CustomLoginController.logout()");
		System.out.println("mapObject before clear :: " + loginMap);
		loginMap.clear();
		model.addAttribute(LOGOUT_MSG, "You Logged Off");
		System.out.println("mapObject after clear :: " + loginMap);
		return LOGIN_PAGE;
	}

	private String getUserWishMessage() {
		String wishMessage = wmService.getWishMessage();
		return wishMessage;
	}

	@GetMapping(FORGET_URL)
	public String getEmailForgetPage() {
		return EMAIL_FORGET_PAGE;
	}

}
