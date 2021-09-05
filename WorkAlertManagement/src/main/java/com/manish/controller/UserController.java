package com.manish.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final String USERNAME = "username";
	
	private CustomLoginController customLoginController;

	private Map<String, String> map = new HashMap<String, String>();
	
	@Autowired
	public UserController(CustomLoginController customLoginController) {
		super();
		this.customLoginController = customLoginController;
	}

	public String getUsername() {
		map = customLoginController.loginMap;
		String username = StringUtils.EMPTY;

		if (map.get(USERNAME) != null) {
			username = map.get(USERNAME).toString();
		}
		return username;
	}
	
	
}
