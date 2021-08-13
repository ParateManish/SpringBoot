package com.manish.service;

import java.util.Calendar;

import org.springframework.stereotype.Service;

@Service
public class UserWishMessageService {

	public String getWishMessage() {
		Calendar calender = Calendar.getInstance();
		int currentTime = calender.get(Calendar.HOUR_OF_DAY);

		if (currentTime <= 12) {
			System.out.println("Good Morning");
			return "Good Morning";
		} else if (currentTime <= 16) {
			System.out.println("Good Afternoon");
			return "Good Afternoon";
		} else {
			System.out.println("Good Evening");
			return "Good Evening";
		}
	}
}
