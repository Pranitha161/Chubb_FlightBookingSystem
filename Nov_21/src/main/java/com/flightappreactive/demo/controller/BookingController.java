package com.flightappreactive.demo.controller;


import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightappreactive.demo.entity.Booking;
import com.flightappreactive.demo.service.BookingService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/flight")
public class BookingController {
	
	private final BookingService bookingService;

	@PostMapping("booking/{flightId}")
	public Mono<Map<String,String>> bookTicket(@RequestBody Booking booking,@PathVariable Integer flightId) {
		return  bookingService.bookTicket(flightId, booking);
	}
	@GetMapping("ticket/{pnr}")
	public Mono<Booking> getByPnr(@PathVariable String pnr){
		return bookingService.getTicketsByPnr(pnr);
	}
	@GetMapping("/history/{emailId}")
	public Mono<Booking> getByemailId(@PathVariable String emailId){
		return bookingService.getBookingsByemailId(emailId);
	}
	@DeleteMapping("/booking/cancel/{pnr}")
	public Mono<Void> cancelBooking(@PathVariable String pnr) {
		return bookingService.deleteBooking(pnr);
	}
}
