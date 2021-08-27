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

	// BELOW ARE THE CONSTANTS FOR THE URL
	private static final String GET_PROFILE_URL = "/getProfile";
	private static final String UPDATE_URL = "/update";

	// BELOW ARE THE CONSTANTS FOR THE HTML PAGE
	private static final String PROFILE_PAGE = "profilePage";

	// BELOW ARE THE CONSTANTS FOR THE MODEL ATTRIBUTE
	private static final String UPDATE_MSG = "updateMsg";
	private static final String USER = "user";

	// OTHER CONSTANTS
	private static final String USERNAME = "username";
	private static final String USER_AND_ADMIN = "User and Admin";
	private static final String USER_ONLY = "User only";

	private UserLoginDBService dbService;
	private CustomLoginController customLoginController;
	private AlertController alertController;

	public Map<String, String> map = new HashMap<String, String>();

	@Autowired
	public ProfileController(UserLoginDBService dbService, CustomLoginController customLoginController,
			AlertController alertController) {
		super();
		this.dbService = dbService;
		this.customLoginController = customLoginController;
		this.alertController = alertController;
	}

	@GetMapping(GET_PROFILE_URL)
	public String getProfile(Model model) {

		String resultPage = alertController.checkUserLogin(model);
		if (!StringUtils.isBlank(resultPage)) {
			return resultPage;
		}

		UserLogin user = dbService.getUser(getUsername());
		model.addAttribute(USER, user);
		return PROFILE_PAGE;
	}

	private String getUsername() {
		map = customLoginController.loginMap;
		String username = StringUtils.EMPTY;

		if (map.get(USERNAME) != null) {
			username = map.get(USERNAME).toString();
		}
		return username;
	}

	@PostMapping(UPDATE_URL)
	public String updateUserProfile(@ModelAttribute UserLogin user, Model model) {
		String userRole = StringUtils.EMPTY;
		if (user.getIsAdmin() != null && user.getIsAdmin().equals("1"))
			userRole = USER_AND_ADMIN;
		else
			userRole = USER_ONLY;
		user.setIsAdmin(userRole);
		dbService.updateUser(user);
		model.addAttribute(UPDATE_MSG, "Your Profile is Updated");
		model.addAttribute(USER, user);
		return PROFILE_PAGE;
	}

}
