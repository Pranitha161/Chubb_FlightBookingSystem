package com.flightappreactive.demo.service;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.flightappreactive.demo.entity.Flight;
import com.flightappreactive.demo.entity.SearchRequest;
import com.flightappreactive.demo.repository.AirLineRepository;
import com.flightappreactive.demo.repository.FlightRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FlightService {
	private final AirLineRepository airlineRepo;
	private final FlightRepository flightRepo;
	FlightService(AirLineRepository airlineRepo,FlightRepository flightRepo){
		this.airlineRepo=airlineRepo;
		this.flightRepo=flightRepo;
	}
	public Flux<Flight> search(SearchRequest searchRequest){
		return flightRepo.findAll();
	}
	
	public Mono<Flight> addFlight(Flight flight) {
		
	    return Mono.just(flight.getAirlineId())
	               .flatMap(airlineRepo::findById)
	               .switchIfEmpty(Mono.error(new ResponseStatusException(
	                   HttpStatus.BAD_REQUEST, "No airline present to add the flight")))
	               .map(airline -> {
	            	   flight.setAirlineId(airline.getId());
//	                   flight.initializeSeats(flight.getAvailableSeats() / 6, 6);
	                   return flight;
	               })
	               .flatMap(flightRepo::save);
	}
}
