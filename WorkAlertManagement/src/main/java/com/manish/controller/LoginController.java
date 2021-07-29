package com.manish.controller;

import java.util.Map;

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

	@Autowired
	private UserLoginDBService dbService;

	@GetMapping("/home")
	public String getHomePage() {
		return "home";
	}

	@GetMapping("/getSignupPage")
	public String getSignUpPage() {
		return "signUpPage";
	}

	@PostMapping("/saveUser")
	public String registerUser(@ModelAttribute UserLogin user, Model model) {
		dbService.saveUser(user);
		String message = "Hello " + user.getUserName() + " You are Successfully Registerd";
		model.addAttribute("message", message);
		return "home";
	}

	@GetMapping("/getLoginPage")
	public String getLoginPage() {
		return "loginPage";
	}

	@PostMapping("/login")
	public String checkUser(@ModelAttribute UserLogin user, Model model) {
		Map<String, String> map = dbService.getUser(user.getUserName(), user.getPassword());

		if (map.get("message") != null) {
			model.addAttribute("message", map.get("message"));
			return "home";
		} else
			return "welcome";
	}

}
