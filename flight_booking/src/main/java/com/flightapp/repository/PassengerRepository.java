package com.flightapp.repository;



import org.springframework.data.repository.CrudRepository;

import com.flightapp.entity.Passenger;

public interface PassengerRepository extends CrudRepository<Passenger, Integer>{
	Passenger findByEmail(String email);
}
