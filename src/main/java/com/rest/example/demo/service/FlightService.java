package com.rest.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rest.example.demo.dao.FlightDto;
import com.rest.example.demo.exception.FlightNotFoundException;
import com.rest.example.demo.model.Airport;
import com.rest.example.demo.model.Flight;
import com.rest.example.demo.model.FlightStatus;
import com.rest.example.demo.repository.FlightRepository;
import com.rest.example.demo.util.AppUtil;
import com.rest.example.demo.util.SupportedQueryPattern;
import com.rest.example.demo.web.AirportController;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FlightService {
	
	Logger logger = LogManager.getLogger(FlightService.class);

	private FlightRepository flightRepository;

	private AirportService airportService;

	public FlightService(FlightRepository flightRepository, AirportService airportService) {
		this.flightRepository = flightRepository;
		this.airportService = airportService;
	}

	/**
	 * TODO further chance to improve using specification builder pattern
	 * @param status
	 * @param flightNumber
	 * @param departure
	 * @param arrival
	 * @param flexiTravel
	 * @return
	 */
	public List<FlightDto> findFlight(String status, String flightNumber, String departure, String arrival,
			String flexiTravel) {

		Specification<Flight> spec = Specification.where(null);
		Airport departureAirport = null;
		Airport arrivalAirport = null;
		if (departure != null) {

			departureAirport = airportService.getAirportByCode(departure);
		}
		if (arrival != null) {
			arrivalAirport = airportService.getAirportByCode(arrival);
		}

		SupportedQueryPattern supportedPattern = AppUtil.getQueryPattern(status, flightNumber, departure, arrival,
				flexiTravel);

		switch (supportedPattern) {
		case STATUS:
			spec = Specification.where(FlightSpecification.hasStatus(status));
			break;
		case FLIGHTNUMBER:
			spec = Specification.where(FlightSpecification.hasFlightNumber(flightNumber));
			break;
		case DEPARTURE_AND_ARRIVAL:
			spec = Specification.where(FlightSpecification.hasDepartureAirport(departureAirport))
					.and(FlightSpecification.hasArrivalAirport(arrivalAirport));
			break;
		case STATUS_AND_DEPARTURE_AND_ARRIVAL:
			spec = Specification.where(FlightSpecification.hasStatus(status))
					.and(FlightSpecification.hasDepartureAirport(departureAirport))
					.and(FlightSpecification.hasArrivalAirport(arrivalAirport));
			break;

		case STATUS_AND_DEPARTURE_OR_ARRIVAL:
			logger.info("Inside required query");
			spec = Specification.where(FlightSpecification.hasStatus(status)).and(FlightSpecification
					.hasDepartureAirport(departureAirport).or(FlightSpecification.hasArrivalAirport(arrivalAirport)));
		case DEPARTURE :
			  spec = Specification.where(FlightSpecification.hasDepartureAirport(departureAirport));
			  break;
		case ARRIVAL :
			spec = Specification.where(FlightSpecification.hasArrivalAirport(arrivalAirport));
			break;
		default:
			break;
		}

		List<Flight> flights = flightRepository.findAll(spec, Sort.by("duration").ascending());
		List<FlightDto> flightDtos = flights.stream().map((flight) -> this.convertToFlightDto(flight, new FlightDto()))
				.collect(Collectors.toList());
		return flightDtos;
	}

	public FlightDto save(FlightDto flightDto) {

		Flight flight = new Flight();

		flight = flightRepository.save(convertToFlight(flightDto, flight));

		return convertToFlightDto(flight, flightDto);

	}

	/**
	 * TODO add exception handling if Flight not found for provided id
	 * 
	 * @param id
	 */

	public void deleteFlight(Long id) {

		if (flightRepository.existsById(id)) {
			flightRepository.deleteById(id);
		} else {
			throw new FlightNotFoundException("Flight not exist for provided id:" + String.valueOf(id));
		}

	}

	/**
	 * TODO add exception handing for invalid airports and date
	 * 
	 * @param id
	 * @param flightDto
	 * @return
	 */
	public Flight updateFlight(Long id, FlightDto flightDto) {

		Flight flight = flightRepository.getReferenceById(id);
		if (flight != null) {

			return flightRepository.save(convertToFlight(flightDto, flight));

		} else

		{
			throw new FlightNotFoundException("Flight not found with id:" + String.valueOf(id));
		}

	}

	/**
	 * TODO use ModelMapper or BeanUtils for effective transformation
	 * 
	 * @param flight
	 * @param flightDto
	 * @return
	 */
	public FlightDto convertToFlightDto(Flight flight, FlightDto flightDto) {
		flightDto.setId(flight.getId());
		flightDto.setFlightNumber(flight.getFlightNumber());
		flightDto.setArrival(flight.getArrival().getAirportCode());
		flightDto.setDeparture(flight.getDeparture().getAirportCode());
		flightDto.setDepartureTime(flight.getDepartureTime().format(AppUtil.formatter));
		flightDto.setArrivalTime(flight.getArrivalTime().format(AppUtil.formatter));
		flightDto.setStatus(flight.getStatus().name());
		flightDto.setDuration(flight.getDuration());

		return flightDto;
	}

	/**
	 * TODO use ModelMapper or BeanUtils for effective transformation
	 * 
	 * @param flightDto
	 * @param flight
	 * @return
	 */
	public Flight convertToFlight(FlightDto flightDto, Flight flight) {
		if (flightDto.getFlightNumber() != null) {
			flight.setFlightNumber(flightDto.getFlightNumber());
		}
		if (flightDto.getDeparture() != null) {

			flight.setDeparture(airportService.getAirportByCode(flightDto.getDeparture()));
		}
		if (flightDto.getArrival() != null) {

			flight.setArrival(airportService.getAirportByCode(flightDto.getArrival()));
		}
		if (flightDto.getDepartureTime() != null) {

			flight.setDepartureTime(AppUtil.convertToDate(flightDto.getDepartureTime()));
		}
		if (flightDto.getArrivalTime() != null) {

			flight.setArrivalTime(AppUtil.convertToDate(flightDto.getArrivalTime()));
		}
		if (flightDto.getStatus() != null) {

			flight.setStatus(FlightStatus.valueOf(flightDto.getStatus()));
		}
		return flight;
	}

}
