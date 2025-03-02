package com.rest.example.demo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.context.request.WebRequest;

public class AppUtil {

	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static LocalDateTime convertToDate(String date) {

		return LocalDateTime.parse(date, formatter);

	}

	public static boolean hasQueryParametersSpecified(WebRequest webRequest) {
		return webRequest.getParameterMap().size() > 0;
	}

	public static boolean areSupportedqueryparamsProvided(Set<String> parameterSet) {
        List<String> supportedparamList = Arrays.asList("status","departure","arrival","flightNumber","flexiTravel");
        return  (parameterSet.stream().filter((param)-> supportedparamList.contains(param)).collect(Collectors.toSet()).size() == parameterSet.size());
	}

}