package com.rest.example.demo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.rest.example.demo.model.Airport;
import com.rest.example.demo.service.AirportService;
import com.rest.example.demo.util.AppUtil;

@RestController
@RequestMapping("/airports")
@Validated
public class AirportController {

	Logger logger = LogManager.getLogger(AirportController.class);

	@Autowired
	private AirportService airportService;

	@PostMapping
	public Airport saveAirport(@RequestBody Airport airport) {

		return airportService.saveAirport(airport);
	}

	@GetMapping
	public ResponseEntity<List<Airport>> getAirPorts(@RequestParam(required = false) String code,
			WebRequest webRequest) {

		List<Airport> airports = new ArrayList<Airport>();

		if (AppUtil.hasQueryParametersSpecified(webRequest)) {

			airports.add(airportService.getAirportByCode(code));

		} else {
			airports = airportService.getAllAirports();
		}

		return new ResponseEntity<List<Airport>>(airports, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
		Optional<Airport> airPort = airportService.getAirportById(id);

		return airPort.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
