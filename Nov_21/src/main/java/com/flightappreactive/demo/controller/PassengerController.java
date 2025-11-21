package com.flightappreactive.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightappreactive.demo.entity.Passenger;
import com.flightappreactive.demo.service.PassengerService;

import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/flight")
@Table(name="passengers")
public class PassengerController {
	private final PassengerService passengerService;
	@GetMapping("/get/{passengerId}")
	public Mono<Passenger> getPassenger(@PathVariable Integer passengerId) {    
	    return passengerService.getPassengerById(passengerId); 
	}
	@GetMapping("/get/{email}")
	public Mono<Passenger> getPassenger(@PathVariable String email) {    
	    return passengerService.getPassengerByEmail(email); 
	}
	@PostMapping("/add")
	public Mono<Passenger> addPassenger(@RequestBody @Valid Passenger p ){
		return passengerService.savePassenger(p);
	}
	@PostMapping("/update/{passengerId}")
	public Mono<Passenger> updatePassenger(@RequestBody @Valid Passenger p,@PathVariable Integer passengerId){
		return passengerService.updateById(p,passengerId);
	}
	@DeleteMapping("/delete/{passengerId}")
	public Mono<Void> deletePassenger(@PathVariable Integer passengerId){
		return passengerService.deleteById(passengerId);
	}
	
}
