package com.flightapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.flightapp.entity.Airline;

public interface AirLineRepository extends CrudRepository<Airline, Integer>{

}
