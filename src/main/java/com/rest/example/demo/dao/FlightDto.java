package com.rest.example.demo.dao;

import org.springframework.data.annotation.ReadOnlyProperty;

public class FlightDto {

	@ReadOnlyProperty
	private long id;
	
	private String flightNumber;
	
	private String status;

	private String departure;

	private String arrival;

	private String departureTime;

	private String arrivalTime;
	
	@ReadOnlyProperty
	private long duration;

	public FlightDto(String flightNumber, String status, String departure, String arrival, String departureTime,
			String arrivalTime) {
		this.flightNumber = flightNumber;
		this.status = status;
		this.departure = departure;
		this.arrival = arrival;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}
	

	public FlightDto() {

	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

}
