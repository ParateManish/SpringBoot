package com.manish.service;

import java.util.Calendar;

import org.springframework.stereotype.Service;

@Service
public class UserWishMessageService {

	private static final String GOOD_MORNING = "Good Morning";
	private static final String GOOD_AFTERNOON = "Good Afternoon";
	private static final String GOOD_EVENING = "Good Evening";

	public String getWishMessage() {
		Calendar calender = Calendar.getInstance();
		int currentTime = calender.get(Calendar.HOUR_OF_DAY);

		if (currentTime <= 12) {
			System.out.println(GOOD_MORNING);
			return GOOD_MORNING;
		} else if (currentTime <= 16) {
			System.out.println(GOOD_AFTERNOON);
			return GOOD_AFTERNOON;
		} else {
			System.out.println(GOOD_EVENING);
			return GOOD_EVENING;
		}
	}
}
