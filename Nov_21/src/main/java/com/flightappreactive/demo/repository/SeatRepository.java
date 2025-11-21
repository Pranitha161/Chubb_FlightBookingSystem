package com.flightappreactive.demo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.flightappreactive.demo.entity.Seat;

import reactor.core.publisher.Flux;

public interface SeatRepository extends ReactiveCrudRepository<Seat, Integer> {
    Flux<Seat> findByFlightId(Integer flightId);
}