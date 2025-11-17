package com.flightapp.service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.*;
import com.flightapp.entity.Booking;import com.flightapp.entity.Flight;
import com.flightapp.entity.Seat;
import com.flightapp.entity.enums.TripType;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightRepository;
import com.flightapp.repository.PassengerRepository;

import jakarta.transaction.Transactional;
@Service
@Slf4j
public class BookingService {
	@Autowired
	private BookingRepository bookingRepo;
	@Autowired
	private FlightRepository flightRepo;
	@Transactional
	public ResponseEntity<?> addticket(int flightId,Booking booking) {
		Map<String,String> message=new HashMap<>();
		
		return (ResponseEntity<?>) flightRepo.findById(flightId).map(flight->{
			List<String> requestSeats=booking.getSeatNumbers();
			List<Seat> flightSeats=flight.getSeats();
			for(String seatNum:requestSeats) {
				Seat seat=flightSeats.stream().filter(s->s.getSeatNumber().equals(seatNum)).findFirst().orElse(null);
			
			if(seat==null||!seat.isAvailable()) {
				message.put("message", "Seat "+seatNum+" is not available");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
			}
			}
			for(String seatNum:requestSeats) {
				flightSeats.stream().filter(s->s.getSeatNumber().equals(seatNum)).findFirst().ifPresent(s->s.setAvailable(false));
			}
			
				flight.setAvailableSeats(flight.getAvailableSeats()-booking.getSeatCount());
				flightRepo.save(flight);
				booking.setFlight(flight);
				booking.setSeatCount(requestSeats.size());
				String pnr=flight.getId()+"-"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
				if(booking.getTripType()== TripType.ROUND_TRIP) {
					booking.setTotalAmount(flight.getPrice().getRoundTrip()*booking.getSeatCount());
				}
				else if(booking.getTripType()==TripType.ONE_WAY) {
					booking.setTotalAmount(flight.getPrice().getOneWay()*booking.getSeatCount());
				}
				booking.setPnr(pnr);
				bookingRepo.save(booking);
				message.put("message","Ticket booking successful with pnrId: "+pnr);
				message.put("Total amount:"," "+booking.getTotalAmount());
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);

			
		}).orElseGet(()->{
						message.put("message", "FlightId not found to book ticket");
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
				});
	
	}
	public ResponseEntity<?> getTicketsByPnr(String pnr) {
	    Map<String, String> response = new HashMap<>();

	    Optional<Booking> bookingOpt = bookingRepo.findByPnr(pnr);

	    if (bookingOpt.isPresent()) {
	        return ResponseEntity.ok(bookingOpt.get());
	    } else {
	        response.put("message", "PNR is not found");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	}

	public ResponseEntity<?> getBookingsByemailId(String emailId){
		 Map<String, String> response = new HashMap<>();
		if(bookingRepo.findByEmail(emailId).isEmpty()) {
			response.put("message", "No booking with the element email"+emailId);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		else {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookingRepo.findByEmail(emailId));
					
		}
	}
	public ResponseEntity<?> deleteBooking(String pnr) {
		Map<String, String> response = new HashMap<>();
		Optional<Booking> booking=bookingRepo.findByPnr(pnr);
		if(booking.isEmpty()) {
			response.put("message", "No booking with pnr "+pnr);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		else {
			Flight flight=booking.get().getFlight();
			LocalDateTime time=flight.getDepartureTime();
			if(time.isAfter(LocalDateTime.now().plusHours(24))) {
				for(String seatNum:booking.get().getSeatNumbers()) {
					Optional<Seat> seatOpt = flight.getSeats().stream()
						    .filter(s -> s.getSeatNumber().equals(seatNum))
						    .findFirst();
						if (seatOpt.isPresent()) {
						    Seat seat = seatOpt.get();
						    seat.setAvailable(true);
						}
				}
			flight.setAvailableSeats(flight.getAvailableSeats()+booking.get().getSeatCount());
			flightRepo.save(flight);
			bookingRepo.delete(booking.get());
			response.put("message","Booking deleted successfully with pnr "+pnr);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
			}
			else {
				response.put("message", "Could not delete the booking as the time is less than 24 hours.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		}
			
			
			
	
	}
}
