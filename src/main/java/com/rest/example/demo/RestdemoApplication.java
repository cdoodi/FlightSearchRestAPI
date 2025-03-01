package com.rest.example.demo;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import com.rest.example.demo.model.Airport;
import com.rest.example.demo.model.Flight;
import com.rest.example.demo.model.FlightStatus;
import com.rest.example.demo.repository.AirportRepository;
import com.rest.example.demo.repository.FlightRepository;

@SpringBootApplication
public class RestdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestdemoApplication.class, args);
	}

	/**
	 * TODO extract the data to CSV and import
	 * @param airportRepository
	 * @param flightRepository
	 * @return
	 */
	@Bean
	@Order(1)
	CommandLineRunner initData(AirportRepository airportRepository, FlightRepository flightRepository) {
		return (args) -> {

			Airport airport1 = new Airport("IND-DLH", "Delhi International airport");
			Airport airport2 = new Airport("IND-HYD", "Rajiv Gandhi Hyderabad International airport");
			Airport airport3 = new Airport("IND-CHN", "Chennai International airport");

			airportRepository.save(airport1);
			airportRepository.save(airport2);
			airportRepository.save(airport3);
			
			LocalDateTime departureTime = LocalDateTime.now().plusDays(2);
			LocalDateTime arrivalTime = LocalDateTime.now().plusDays(4);
			
			Flight flight1 = new Flight("KLM-13F",airport1,airport2,departureTime,arrivalTime,FlightStatus.SCHEDULED);
			
			Flight flight2 = new Flight("KLM-291F",airport1,airport3,departureTime.plusWeeks(10),arrivalTime.plusWeeks(11),FlightStatus.CANCELLED);
			
			Flight flight3 = new Flight("KLM-7438F",airport2,airport3,departureTime.plusWeeks(1),arrivalTime.plusWeeks(3),FlightStatus.SCHEDULED);
			
			
			flightRepository.save(flight1);
			flightRepository.save(flight2);
			flightRepository.save(flight3);

		};
	}

	
}
