package com.flightappreactive.demo.service;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.flightappreactive.demo.entity.Airline;
import com.flightappreactive.demo.repository.AirLineRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AirlineService {
	private final AirLineRepository airlineRepo;
	AirlineService(AirLineRepository airlineRepo){
		this.airlineRepo=airlineRepo;
	}
	public Mono<Airline> addAirline(Airline airline){
		return airlineRepo.existsByLogoUrl(airline.getLogoUrl()).flatMap(exists->{
			if(exists) {
				return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Airline already exists"));
			}
			return airlineRepo.save(airline);
		});
		
	}
	public Flux<Airline> getAllAirLines(){
		return airlineRepo.findAll();
	}
}
