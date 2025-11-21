package com.flightappreactive.demo.repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.flightappreactive.demo.entity.Passenger;
import reactor.core.publisher.Mono;
public interface PassengerRepository extends ReactiveCrudRepository<Passenger, Integer> {
	Mono<Passenger> findByEmail(String email);
}
