package com.rest.example.demo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Airport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(unique=true)
	private String code;

	@NotNull
	private String name;

	public Airport() {

	}
	
	public Airport(String airportCode, @NotNull String airportName) {
		super();
		this.code = airportCode;
		this.name = airportName;
	}

	public String getAirportCode() {
		return code;
	}

	public void setAirportCode(String airportCode) {
		this.code = airportCode;
	}

	public String getAirportName() {
		return name;
	}

	public void setAirportName(String airportName) {
		this.name = airportName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
