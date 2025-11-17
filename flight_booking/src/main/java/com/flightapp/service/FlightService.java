package com.flightapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

import com.flightapp.entity.Flight;
import com.flightapp.entity.SearchRequest;
import com.flightapp.repository.AirLineRepository;
import com.flightapp.repository.FlightRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FlightService {
	@Autowired
    private AirLineRepository airlineRepo;
	@Autowired
	private FlightRepository flightRepo;
	
	
	
	public List<Flight> search(SearchRequest searchRequest){
		List<Flight> flights=flightRepo.getFlightByFromPlaceAndToPlace(searchRequest.getFromPlace(), searchRequest.getToPlace());
		return flights.stream().filter(flight->flight.getArrivalTime().toLocalDate().equals(searchRequest.getDate())).collect(Collectors.toList());
	}
	public List<Flight> getFlights(){
		return  (List<Flight>) flightRepo.findAll();
	}
	public ResponseEntity<Map<String,String>> addFlight(Flight flight) {
		return airlineRepo.findById(flight.getAirline().getId())
				.map(
				existingAirLine->{
					flight.setAirline(existingAirLine);
					flight.initializeSeats(flight.getAvailableSeats()/6, 6);
					Flight savedFlight=flightRepo.save(flight);
					String flightId="FL"+savedFlight.getId()+savedFlight.getAirline().getName().substring(0,3).toUpperCase();
					Map<String,String> response=new HashMap<>();
					response.put("message", "Flight inventory added successfully");
					response.put("flightId", flightId);
					return ResponseEntity.status(HttpStatus.CREATED).body(response);
				})
				.orElseGet(()->{
					Map<String,String> errorResponse=new HashMap<>();
					errorResponse.put("message", "No airline present to add the flight");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
				});
	}
}
