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

	@Autowired
	private UserLoginDBService dbService;
	
	@Autowired
	private IUserLoginRepository userRepo;
	
	@Autowired
	private UserWishMessageService wmService;
	
	private Map<String, String> loginMap = new HashMap<String, String>();

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

	/*	@PostMapping("/login")
		public String checkUser(@ModelAttribute UserLogin user, Model model) {
			Map<String, String> map = dbService.getUser(user.getUserName(), user.getPassword());
	
			if (map.get("message") != null) {
				model.addAttribute("message", map.get("message"));
				return "loginPage";
			} else
				return "welcome";
		}*/

	@PostMapping("/customLogin")
	public String checkUserLogin(@ModelAttribute UserLogin user, Model model) {
		System.out.println("LoginController.checkUser()");
		
		UserLogin userDB = userRepo.findByUserName(user.getUserName());	
		if (userDB == null) {
			System.err.println("user not found");
			throw new UsernameNotFoundException("User not found");
		}else {
			String userWishMessage = getUserWishMessage();
			model.addAttribute("wishMsg",userWishMessage);
			model.addAttribute("username",userDB.getUserName());
			System.out.println("user is found :: "+userDB.toString());
			
		}

		
		 if(!StringUtils.isBlank(user.getUserName())) {
			Map<String, String> dbUser = dbService.getUser(user.getUserName(), user.getPassword());
			loginMap.put("username", user.getUserName());
		}else {
			System.out.println("User is not logged In");
			String userNotLoginMsg = "Please Login";
			model.addAttribute("userNotLoginMsg",userNotLoginMsg);
		}
			return "welcome";
	}
	
	public void checkUserLogin() throws Exception {
		System.out.println("CustomLoginController.checkUserLogin()-START");
		String username = StringUtils.EMPTY;
		if(loginMap.get("username")!=null) {
			username = loginMap.get("username").toString();
		}
		System.out.println("Username :: "+username);
		if(StringUtils.isBlank(username) || username==null) {
			System.err.println("Please Login");
			throw new Exception("Please Login");
		}
		System.out.println("CustomLoginController.checkUserLogin()-END");
	}
	
	@GetMapping("/customLogout")
	public String logout(Model model) {
		System.out.println("CustomLoginController.logout()");
		System.out.println("mapObject before clear :: "+loginMap);
		loginMap.clear();
		model.addAttribute("logoutMsg","You Logged Off");
		System.out.println("mapObject after clear :: "+loginMap);
		return "loginPage";
	}
	
	private String getUserWishMessage() {
		String wishMessage = wmService.getWishMessage();
		return wishMessage;
	}
}
