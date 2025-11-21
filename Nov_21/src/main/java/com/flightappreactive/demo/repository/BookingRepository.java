package com.flightappreactive.demo.repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.flightappreactive.demo.entity.Booking;
import reactor.core.publisher.Mono;
public interface BookingRepository extends ReactiveCrudRepository<Booking, Integer> {
	Mono<Booking> findByPnr(String pnr);
	Mono<Booking> findByEmail(String email);
}
