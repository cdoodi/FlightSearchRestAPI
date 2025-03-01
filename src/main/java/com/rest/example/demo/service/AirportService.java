package com.rest.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rest.example.demo.exception.AirportNotFoundException;
import com.rest.example.demo.model.Airport;
import com.rest.example.demo.repository.AirportRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AirportService {

	private final AirportRepository airportRepository;

	public AirportService(AirportRepository airportRepository) {
		this.airportRepository = airportRepository;

	}

	public Airport saveAirport(Airport airport) {

		return airportRepository.save(airport);

	}

	public Airport getAirportByCode(String airportCode) {
		
		Optional<Airport> airport = airportRepository.findByCode(airportCode);
		if(airport.isPresent()) {
			return airport.get();
		}
		else {
			throw new AirportNotFoundException("Airport not found with code:"+airportCode);
		}

	}
	
	public List<Airport> getAllAirports(){
		
		return airportRepository.findAll();
	}

	public Optional<Airport> getAirportById(Long id) {
		
		return airportRepository.findById(id);
	}
}
