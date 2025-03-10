package com.rest.example.demo.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.rest.example.demo.dao.FlightDto;
import com.rest.example.demo.exception.NotSupportedFlightQueryException;
import com.rest.example.demo.model.Flight;
import com.rest.example.demo.service.FlightService;
import com.rest.example.demo.util.AppUtil;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

	Logger logger = LogManager.getLogger(FlightController.class);

	@Autowired
	private FlightService flightService;

	@GetMapping
	public ResponseEntity<List<FlightDto>> getFlights(@RequestParam(required = false) String departure,
			@RequestParam(required = false) String arrival, @RequestParam(required = false) String flightNumber,
			@RequestParam(required = false) String status,@RequestParam(required = false) String flexiTravel ,WebRequest webRequest) {

		   if(! AppUtil.areSupportedqueryparamsProvided(webRequest.getParameterMap().keySet()))
		   {
			   throw new NotSupportedFlightQueryException("Invalid query params: Supported query params are ->status,departure,arrival,flightNumber");
		   }
		   List<FlightDto> flightDtos =  flightService.findFlight(status, flightNumber,departure,arrival,flexiTravel);
		   
		   return new ResponseEntity<List<FlightDto>>(flightDtos, flightDtos.size()>0?HttpStatus.ACCEPTED:HttpStatus.NO_CONTENT);
		
	}

	@PostMapping
	public FlightDto saveFlight(@RequestBody FlightDto flightDto) {
		return flightService.save(flightDto);
	}

	@PutMapping("/{id}")
	public FlightDto updateFlight(@PathVariable Long id, @RequestBody FlightDto flightDto) {
		
		Flight flight =  flightService.updateFlight(id, flightDto);
		
		return flightService.convertToFlightDto(flight, flightDto);
          	
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
		flightService.deleteFlight(id);
		return ResponseEntity.ok("Flight deleted successfully");
	}
	
	@GetMapping("/{id}")
	public FlightDto getFlight(@PathVariable Long id) {
		return  flightService.getFlightById(id);
	}

}
