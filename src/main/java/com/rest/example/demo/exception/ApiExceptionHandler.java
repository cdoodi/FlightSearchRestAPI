package com.rest.example.demo.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;


/**
 * TODO add more exception handling
 */
@ControllerAdvice
public class ApiExceptionHandler {

	
	@ExceptionHandler(NotSupportedFlightQueryException.class)
	public ResponseEntity<ApiError> handleInvalidFlightQuery(NotSupportedFlightQueryException ex, WebRequest request) {

		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), new Date(), ex.getLocalizedMessage(),
				request.getDescription(false), "Invalid flight query params");

		return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(FlightNotFoundException.class)
	public ResponseEntity<ApiError> handleFlightNotExist(FlightNotFoundException ex, WebRequest request) {

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getLocalizedMessage(),
				request.getDescription(false), "Invalid Flight query");

		return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AirportNotFoundException.class)
	public ResponseEntity<ApiError> handleAirportNotExist(AirportNotFoundException ex, WebRequest request) {

		HttpStatus httpStatus = request.getDescription(false).contains("flights")?HttpStatus.BAD_REQUEST:HttpStatus.NOT_FOUND;
		ApiError apiError = new ApiError(httpStatus.value(), new Date(), ex.getLocalizedMessage(),
				request.getDescription(false), "Airport not found");

		return new ResponseEntity<ApiError>(apiError, httpStatus);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getLocalizedMessage(),
				request.getDescription(false), errors);

		return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
	}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> globalExceptionHandler(Exception ex, WebRequest request) {
      ApiError message = new ApiError(
          HttpStatus.INTERNAL_SERVER_ERROR.value(),
          new Date(),
          ex.getMessage(),
          request.getDescription(false),
          "Unexpected Error");
      
      return new ResponseEntity<ApiError>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}