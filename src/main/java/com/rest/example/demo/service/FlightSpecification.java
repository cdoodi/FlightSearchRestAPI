package com.rest.example.demo.service;

import org.springframework.data.jpa.domain.Specification;

import com.rest.example.demo.model.*;

public class FlightSpecification {

	public static Specification<Flight> hasStatus(String status) {
		return (root, query, criteriaBuilder) ->  {
			if(status == null) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(root.get("status"),FlightStatus.valueOf(status.toUpperCase()));
		
	};
	}

	public static Specification<Flight> hasFlightNumber(String flightNumber) {
		return (root, query, criteriaBuilder) -> {
			if(flightNumber == null) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(root.get("flightNumber"), flightNumber);
		};
	}
	
	public static Specification<Flight> hasDepartureAirport(Airport departure) {
		return (root, query, criteriaBuilder) -> {
			if(departure == null) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(root.get("departure"), departure);
		};
	}
	
	public static Specification<Flight> hasArrivalAirport(Airport arrival) {
		return (root, query, criteriaBuilder) -> {
			if(arrival == null) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(root.get("arrival"), arrival);
		};
	}
}
