package com.manish.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class Scheduler {
	@Scheduled(fixedRate = 1000)
	public void scheduleFixedRateTask() {
		System.err.println("Fixed rate task - " + System.currentTimeMillis() / 1000);
	}
}
