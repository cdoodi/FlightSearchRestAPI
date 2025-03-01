package com.rest.example.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Flight implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String flightNumber;

	@NotNull
	@Enumerated(EnumType.STRING)
	private FlightStatus status;

	@Positive(message = "Arrival date shall be greater than Departure date")
	private long duration;

	@NotNull(message = "departure Airport not provided")
	@JoinColumn(name = "airport_code", referencedColumnName = "code", nullable = false)
	private Airport departure;

	@NotNull(message = "arrival Airport not provided")
	@JoinColumn(name = "airport_code", referencedColumnName = "code", nullable = false)
	private Airport arrival;

	@Future
	@NotNull
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime departureTime;

	@Future
	@NotNull
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime arrivalTime;

	public Flight() {

	}

	public Flight(String flightNumber, Airport departure, Airport arrival, LocalDateTime departureTime,
			LocalDateTime arrivalTime, FlightStatus status) {

		this.flightNumber = flightNumber;
		this.arrival = arrival;
		this.departure = departure;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.status = status;

	}

	@PreUpdate
	@PrePersist
	public void calDuration() {
		if (departureTime != null && arrivalTime != null) {
			duration = departureTime.until(arrivalTime, ChronoUnit.MINUTES);
		}

	}

	public FlightStatus getStatus() {
		return status;
	}

	public void setStatus(FlightStatus status) {
		this.status = status;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public Airport getDeparture() {
		return departure;
	}

	public void setDeparture(Airport departure) {
		this.departure = departure;
	}

	public Airport getArrival() {
		return arrival;
	}

	public void setArrival(Airport arrival) {
		this.arrival = arrival;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
