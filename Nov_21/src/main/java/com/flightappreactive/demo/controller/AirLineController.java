package com.flightappreactive.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightappreactive.demo.entity.Airline;
import com.flightappreactive.demo.entity.Flight;
import com.flightappreactive.demo.service.AirlineService;
import com.flightappreactive.demo.service.FlightService;

import lombok.RequiredArgsConstructor;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1.0/flight/airline")
@RequiredArgsConstructor
public class AirLineController {

	private final FlightService flightService;

	private final AirlineService airlineService;

    
	@PostMapping("/inventory/add")
	
	public Mono<Flight> addInventory(@RequestBody Flight flight) {
		return flightService.addFlight(flight);
	}
	@PostMapping("/add/airline")
	public Mono<Airline> addAirine(@RequestBody Airline airline) {
		return airlineService.addAirline(airline);
	}
	
}
