package com.flightappreactive.demo.service;

import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.flightappreactive.demo.entity.Passenger;
import com.flightappreactive.demo.repository.PassengerRepository;


import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor 
public class PassengerService {
	private final PassengerRepository passengerRepo;
	
	public Mono<Passenger> getPassengerById(Integer passengerId){
		return passengerRepo.findById(passengerId);
	}
	public Mono<Passenger> getPassengerByEmail(String email){
		return passengerRepo.findByEmail(email);
	}
	public Mono<Passenger> savePassenger(Passenger passenger){
		    return passengerRepo.findByEmail(passenger.getEmail())
		            .flatMap(exists -> Mono.<Passenger>error(
		                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered")
		            ))
		            .switchIfEmpty(passengerRepo.save(passenger));  
		}
	public Mono<Passenger> updateById(Passenger p,Integer id){
		return passengerRepo.findById(id)
	            .switchIfEmpty(Mono.error(
	                new ResponseStatusException(HttpStatus.BAD_REQUEST, "No passenger found with id: " + id)))
	            .flatMap(existing -> {
	                existing.setName(p.getName());
	                existing.setEmail(p.getEmail());
	                existing.setAge(p.getAge());
	                return passengerRepo.save(existing);
	            });		
	}
	public Mono<Void> deleteById(Integer id){
		return passengerRepo.findById(id)
	            .switchIfEmpty(Mono.error(
		                new ResponseStatusException(HttpStatus.BAD_REQUEST, "No passenger found with id: " + id)))
		            .flatMap(passengerRepo::delete);		
	}
}
