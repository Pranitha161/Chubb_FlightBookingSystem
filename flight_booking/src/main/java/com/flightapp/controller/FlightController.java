package com.flightapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.entity.Flight;
import com.flightapp.entity.SearchRequest;
import com.flightapp.service.FlightService;
@RestController
@RequestMapping("/api/v1.0/flight/")
public class FlightController {
	@Autowired
	private FlightService flightService;
	@PostMapping("search")
	public ResponseEntity<?> searchFlight(@RequestBody SearchRequest searchRequest){
		List<Flight> flights=flightService.search(searchRequest);
		if(flights.isEmpty()) {
			Map<String,String> errormsg=new HashMap<>();
			errormsg.put("message","no Flights with the given criteria");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errormsg);
		}
		return ResponseEntity.ok(flights);
	}
	@GetMapping("/get")
	public ResponseEntity<?> getFlight(){
		List<Flight> flights = flightService.getFlights();
		if(flights.isEmpty()) {
			Map<String,String> errormsg=new HashMap<>();
			errormsg.put("message","no Flights with the given criteria");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errormsg);
		}
		return ResponseEntity.ok(flights);
	} 
}
