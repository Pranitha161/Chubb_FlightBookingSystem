package com.flightappreactive.demo.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightappreactive.demo.entity.Flight;
import com.flightappreactive.demo.entity.SearchRequest;
import com.flightappreactive.demo.service.FlightService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/flight/")
public class FlightController {
	private final FlightService flightService;
	@PostMapping("search")
	public Flux<Flight> searchFlight(@RequestBody SearchRequest searchRequest){
		return flightService.search(searchRequest);
	}
	@GetMapping("/get")
	public Mono<Flight> getFlight(Flight flight){
		return flightService.addFlight(flight);
	} 
}
