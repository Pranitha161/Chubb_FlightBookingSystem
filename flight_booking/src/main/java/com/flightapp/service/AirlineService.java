package com.flightapp.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightapp.entity.Airline;
import com.flightapp.repository.AirLineRepository;



@Service
public class AirlineService {
	@Autowired
	AirLineRepository airlineRepo;
	public Airline addAirline(Airline airline) {
		return airlineRepo.save(airline);
	}
	public List<Airline> getAllAirLines(){
		return (List<Airline>) airlineRepo.findAll();
	}
	
	
}
