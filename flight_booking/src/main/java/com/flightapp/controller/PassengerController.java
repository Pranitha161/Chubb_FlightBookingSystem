package com.flightapp.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.entity.Passenger;
import com.flightapp.service.PassengerService;

import jakarta.persistence.Table;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/flight/")
@Table(name = "passengers")
public class PassengerController {
	@Autowired
	PassengerService passengerService;
	@GetMapping("/get/{passengerId}")
	public ResponseEntity<?> getPassenger(@PathVariable Integer passengerId) {    
	    return passengerService.getPassengerById(passengerId); 
	}
	@GetMapping("/get/{email}")
	public ResponseEntity<?> getPassenger(@PathVariable String email) {    
	    return passengerService.getPassengerByEmail(email); 
	}
	@PostMapping("/add")
	public ResponseEntity<?> addPassenger(@RequestBody @Valid Passenger p ){
		return passengerService.savePassenger(p);
	}
	@PostMapping("/update/{passengerId}")
	public ResponseEntity<?> updatePassenger(@RequestBody @Valid Passenger p,@PathVariable Integer passengerId){
		return passengerService.updateById(p,passengerId);
	}
	@DeleteMapping("/delete/{passengerId}")
	public ResponseEntity<?> deletePassenger(@PathVariable Integer passengerId){
		return passengerService.deleteById(passengerId);
	}
}
