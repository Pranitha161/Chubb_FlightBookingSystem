package com.flightapp.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.flightapp.entity.Flight;


public interface FlightRepository extends CrudRepository<Flight, Integer>{
	List<Flight> getFlightByFromPlaceAndToPlace(
			String fromPlace,String toPlace);
}
