package com.flightappreactive.demo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.flightappreactive.demo.entity.Airline;

import reactor.core.publisher.Mono;

public interface AirLineRepository extends ReactiveCrudRepository<Airline, Integer>{
	Mono<Boolean> existsByLogoUrl(String logoUrl);
	Mono<Airline> findByLogoUrl(String logoUrl);
}
