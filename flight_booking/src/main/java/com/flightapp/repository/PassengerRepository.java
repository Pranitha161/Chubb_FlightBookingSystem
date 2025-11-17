package com.flightapp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.flightapp.entity.Passenger;

public interface PassengerRepository extends CrudRepository<Passenger, Integer>{
	Passenger findByEmail(String email);
}
