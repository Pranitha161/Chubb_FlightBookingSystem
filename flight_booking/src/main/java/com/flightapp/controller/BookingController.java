package com.flightapp.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.entity.Booking;

import com.flightapp.service.BookingService;
import com.flightapp.service.PassengerService;

@RestController
@RequestMapping("/api/v1.0/flight")
public class BookingController {
	@Autowired
	private BookingService bookingService;
	@Autowired
	private PassengerService passengerService;
	@PostMapping("booking/{flightId}")
	public ResponseEntity<?> bookTicket(@RequestBody Booking booking,@PathVariable Integer flightId) {
		Map<String, String> response = new HashMap<>();
		ResponseEntity<?> pass=passengerService.getPassengerByEmail(booking.getEmail());
		if(pass.getStatusCode()==HttpStatus.BAD_REQUEST) {
			response.put("message", "Not a valid passenger to book the ticket");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		return bookingService.addticket(flightId,booking);
	}
	@GetMapping("ticket/{pnr}")
	public ResponseEntity<?> getByPnr(@PathVariable String pnr){
		return bookingService.getTicketsByPnr(pnr);
	}
	@GetMapping("/history/{emailId}")
	public ResponseEntity<?> getByemailId(@PathVariable String emailId){
		return bookingService.getBookingsByemailId(emailId);
	}
	@DeleteMapping("/booking/cancel/{pnr}")
	public ResponseEntity<?> cancelBooking(@PathVariable String pnr) {
		return bookingService.deleteBooking(pnr);
	}
}
