package com.rest.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rest.example.demo.model.Flight;


@Repository
public interface FlightRepository extends JpaRepository<Flight, Long >, JpaSpecificationExecutor<Flight>{
	
	

}
