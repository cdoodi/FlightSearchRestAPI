package com.rest.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.example.demo.model.Airport;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

	Optional<Airport> findByCode(String airportCode);
	

}
