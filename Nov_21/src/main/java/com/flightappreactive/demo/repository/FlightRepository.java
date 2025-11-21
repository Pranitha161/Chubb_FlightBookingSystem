package com.flightappreactive.demo.repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.flightappreactive.demo.entity.Flight;
import reactor.core.publisher.Flux;
public interface FlightRepository extends ReactiveCrudRepository<Flight, Integer> {
	Flux<Flight> getFlightByFromPlaceAndToPlace(
			String fromPlace,String toPlace);
}
