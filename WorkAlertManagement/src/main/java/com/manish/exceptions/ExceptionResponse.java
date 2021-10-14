package com.manish.exceptions;

import java.time.LocalDateTime;

public class ExceptionResponse {

	private String message;
	private LocalDateTime timestamp;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getDateTime() {
		return timestamp;
	}

	public void setDateTime(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}