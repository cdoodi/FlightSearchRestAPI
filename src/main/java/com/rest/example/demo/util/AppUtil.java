package com.rest.example.demo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.context.request.WebRequest;

import com.rest.example.demo.exception.NotSupportedFlightQueryException;

public class AppUtil {

	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static LocalDateTime convertToDate(String date) {

		return LocalDateTime.parse(date, formatter);

	}

	public static boolean hasQueryParametersSpecified(WebRequest webRequest) {
		return webRequest.getParameterMap().size() > 0;
	}

	public static boolean isSupportedFlightQuery(WebRequest webRequest) {

		// if(webRequest.getParameterMap().size() == 1 &&
		// webRequest.getParameter(null)))
		return true;
	}

	/**
	 * TODO chances to improve further and include other combinations as required
	 * @param status
	 * @param flightNumber
	 * @param departure
	 * @param arrival
	 * @param flexiTravel
	 * @return
	 */
	public static SupportedQueryPattern getQueryPattern(String status, String flightNumber, String departure,
			String arrival, String flexiTravel) {
		if(status != null && departure != null && arrival != null && flexiTravel != null) {
			return SupportedQueryPattern.STATUS_AND_DEPARTURE_OR_ARRIVAL;
		}
		else if (status != null && departure != null && arrival != null) {
			return SupportedQueryPattern.STATUS_AND_DEPARTURE_AND_ARRIVAL;
		} else if (departure != null && arrival == null && status == null) {

			return SupportedQueryPattern.DEPARTURE;
		}else if(departure == null && arrival != null && status !=null) {
			return SupportedQueryPattern.ARRIVAL;
		}else if(departure != null && arrival != null) {
			return SupportedQueryPattern.DEPARTURE_AND_ARRIVAL;
		} else if (flightNumber != null && status == null) {
			return SupportedQueryPattern.FLIGHTNUMBER;
		} else if (status != null && flightNumber ==null) {
			return SupportedQueryPattern.STATUS;
		} else if (status == null && flightNumber == null && departure == null && arrival == null) {
			return SupportedQueryPattern.ALL;
		}
		throw new NotSupportedFlightQueryException(
				"Not Supported Flight query Exception. Supported query: By status, By flightNumber, By departure and arrival, By status and (departure or/and arrival)");
	}

}