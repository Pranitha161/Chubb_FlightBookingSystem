package com.flightapp.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.entity.Airline;
import com.flightapp.entity.Flight;
import com.flightapp.service.AirlineService;
import com.flightapp.service.FlightService;

@RestController
@RequestMapping("/api/v1.0/flight/airline")
public class AirLineController {
	
    
	@Autowired 
	FlightService flightService;
	@Autowired
	AirlineService airlineService;

    
	@PostMapping("/inventory/add")
	public ResponseEntity<Map<String,String>> addInventory(@RequestBody Flight flight) {
		return flightService.addFlight(flight);
	}
	@PostMapping("/add/airline")
	public void addAirLine(@RequestBody Airline airline) {
		airlineService.addAirline(airline);
	}
	
}
