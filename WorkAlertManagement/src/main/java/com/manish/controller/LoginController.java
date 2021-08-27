package com.manish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manish.model.UserLogin;
import com.manish.service.UserLoginDBService;

@Controller
@RequestMapping("/userLogin")
public class LoginController {

	// BELOW ARE THE CONSTANTS FOR THE URL
	private static final String HOME_URL = "/home";
	private static final String SINGUP_PAGE_URL = "/getSignupPage";
	private static final String SAVEUSER_URL = "/saveUser";
	private static final String LOGIN_PAGE_URL = "/getLoginPage";
	private static final String LOGIN_URL = "/login";
	
	// BELOW ARE THE CONSTANTS FOR THE HTML PAGE
	private static final String HOME_PAGE = "home";
	private static final String SIGNUP_PAGE = "signUpPage";
	private static final String LOGIN_PAGE = "loginPage";
	private static final String WELCOME_PAGE = "welcome";
	
	// BELOW ARE THE CONSTANTS FOR THE MODEL ATTRIBUTE
	private static final String MESSAGE = "message";
	
	private UserLoginDBService dbService;

	@Autowired
	public LoginController(UserLoginDBService dbService) {
		this.dbService = dbService;
	}

	@GetMapping(HOME_URL)
	public String getHomePage() {
		return HOME_PAGE;
	}

	@GetMapping(SINGUP_PAGE_URL)
	public String getSignUpPage() {
		return SIGNUP_PAGE;
	}

	@PostMapping(SAVEUSER_URL)
	public String registerUser(@ModelAttribute UserLogin user, Model model) {
		dbService.saveUser(user);
		String message = "Hello " + user.getUserName() + " You are Successfully Registerd";
		model.addAttribute(MESSAGE, message);
		return HOME_PAGE;
	}

	@GetMapping(LOGIN_PAGE_URL)
	public String getLoginPage() {
		return LOGIN_PAGE;
	}

	/*	@PostMapping("/login")
		public String checkUser(@ModelAttribute UserLogin user, Model model) {
			Map<String, String> map = dbService.getUser(user.getUserName(), user.getPassword());
	
			if (map.get("message") != null) {
				model.addAttribute("message", map.get("message"));
				return "loginPage";
			} else
				return "welcome";
		}*/

	@GetMapping(LOGIN_URL)
	public String checkUser(@ModelAttribute UserLogin user, Model model) {
		System.out.println("LoginController.checkUser()");
			return WELCOME_PAGE;
	}
	
}
