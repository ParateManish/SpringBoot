package com.manish.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/profile")
public class ProfileController {

	private UserLoginDBService dbService;
	private CustomLoginController customLoginController;
	private  AlertController alertController;
	
	@Autowired
	public ProfileController(UserLoginDBService dbService, CustomLoginController customLoginController,
			AlertController alertController) {
		super();
		this.dbService = dbService;
		this.customLoginController = customLoginController;
		this.alertController = alertController;
	}

	public Map<String, String> map = new HashMap<String, String>();
	
	@GetMapping("/getProfile")
	public String getProfile(Model model) {
		
		String resultPage = alertController.checkUserLogin(model);
		if (!StringUtils.isBlank(resultPage)) {
			return resultPage;
		}
		
		UserLogin user = dbService.getUser(getUsername());
		model.addAttribute("user",user);
		return "profilePage";
	}

	private String getUsername() {
		map = customLoginController.loginMap;
		String username = StringUtils.EMPTY;

		if (map.get("username") != null) {
			username = map.get("username").toString();
		}
		return username;
	}
	
	@PostMapping("/update")
	public String updateUserProfile(@ModelAttribute UserLogin user , Model model) {
		String userRole = StringUtils.EMPTY;
		if(user.getIsAdmin() != null && user.getIsAdmin().equals("1"))
			userRole = "User and Admin";
		else
			userRole = "User only";
		user.setIsAdmin(userRole);
		dbService.updateUser(user);
		model.addAttribute("updateMsg","Your Profile is Updated");
		model.addAttribute("user",user);
		return "profilePage";
	}
	
}
