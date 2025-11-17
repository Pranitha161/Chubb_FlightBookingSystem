package com.flightapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.flightapp.entity.Booking;

public interface BookingRepository extends CrudRepository<Booking, Integer>{
	Optional<Booking> findByPnr(String pnr);
	List<Booking> findByEmail(String email);
}
