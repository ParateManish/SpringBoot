package com.manish.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.manish.model.UserLogin;
import com.manish.repository.IUserLoginRepository;

@Service
public class UserLoginDBService {

	@Autowired
	private IUserLoginRepository loginRepo;

	public void saveUser(@ModelAttribute UserLogin login) {
		loginRepo.save(login);
	}

	public Map<String, String> getUser(String userName, String password) {
		Map<String, String> map = new HashMap<>();
		String message = StringUtils.EMPTY;
//		UserLogin user = loginRepo.findByUserNameAndPassword(userName,password);
		UserLogin user = loginRepo.findByUserName(userName);
		
		if (user != null) {
			System.out.println("user is not null");
			if (!StringUtils.equals(user.getPassword(), password)) {
				System.out.println("Entered password is not matched with DB");
				message = "Please Enter Correct Password";
				map.put("message", message);
			}
		} else {
			System.out.println("Username is not available");
			message = "Hello " + userName + " Please Sign Up to Register";
			map.put("message", message);
		}
		return map;
	}
}
