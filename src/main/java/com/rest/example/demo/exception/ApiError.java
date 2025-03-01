package com.rest.example.demo.exception;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ApiError {
	private int statusCode;
	private Date timestamp;

	private String message;
	private List<String> errors;
	private String description;

	public ApiError(int statusCode, Date timestamp, String message, String description, List<String> errors) {
		this.statusCode = statusCode;
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
		this.errors = errors;
	}

	public ApiError(int statusCode, Date timestamp, String message, String description, String errors) {
		this.statusCode = statusCode;
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
		this.errors = Arrays.asList(errors);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}
}
