package com.flightappreactive.demo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.flightappreactive.demo.entity.BookingSeatNumber;

import reactor.core.publisher.Flux;

public interface BookingSeatNumberRepository extends ReactiveCrudRepository<BookingSeatNumber, Integer> {
    Flux<BookingSeatNumber> findByBookingId(Integer bookingId);
}
