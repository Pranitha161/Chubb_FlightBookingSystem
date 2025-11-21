package com.flightappreactive.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.flightappreactive.demo.entity.Booking;
import com.flightappreactive.demo.entity.BookingSeatNumber;
import com.flightappreactive.demo.entity.Seat;
import com.flightappreactive.demo.repository.BookingRepository;
import com.flightappreactive.demo.repository.BookingSeatNumberRepository;
import com.flightappreactive.demo.repository.FlightRepository;
import com.flightappreactive.demo.repository.SeatRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookingService {
	private final BookingRepository bookingRepo;
	private final FlightRepository flightRepo;
	private final SeatRepository seatRepo;
    private final BookingSeatNumberRepository bookingSeatNumberRepo;
    

    public Mono<Map<String, String>> bookTicket(Integer flightId, Booking booking) {
        return flightRepo.findById(flightId)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "FlightId not found")))
            .flatMap(flight -> seatRepo.findByFlightId(flightId).collectList()
                .flatMap(flightSeats -> {
                    List<String> requestSeats = booking.getSeatNumbers(); // @Transient field

                    // validate seat availability
                    for (String seatNum : requestSeats) {
                        Seat seat = flightSeats.stream()
                            .filter(s -> s.getSeatNumber().equals(seatNum))
                            .findFirst()
                            .orElse(null);
                        if (seat == null || !Boolean.TRUE.equals(seat.getAvailable())) {
                            return Mono.error(new ResponseStatusException(
                                HttpStatus.BAD_REQUEST, "Seat " + seatNum + " is not available"));
                        }
                    }

                    // mark seats unavailable
                    flightSeats.stream()
                        .filter(s -> requestSeats.contains(s.getSeatNumber()))
                        .forEach(s -> s.setAvailable(false));

                    flight.setAvailableSeats(flight.getAvailableSeats() - requestSeats.size());

                    // generate PNR
                    String pnr = flight.getId() + "-" +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));

                    // calculate price
                    double price = booking.getTripType().equals("ROUND_TRIP")
                        ? flight.getPriceRoundTrip()
                        : flight.getPriceOneWay();

                    booking.setTotalAmount((int) price * requestSeats.size());
                    booking.setSeatCount(requestSeats.size());
                    booking.setPnr(pnr);
                    booking.setFlightId(flightId);

                    // save flight, seats, booking, and seat numbers
                    return flightRepo.save(flight)
                        .thenMany(seatRepo.saveAll(flightSeats))
                        .then(bookingRepo.save(booking))
                        .flatMap(savedBooking -> bookingSeatNumberRepo.saveAll(
                                requestSeats.stream()
                                    .map(seatNum -> new BookingSeatNumber(null, savedBooking.getId(), seatNum))
                                    .toList()
                            ).collectList()
                            .thenReturn(savedBooking)
                        )
                        .map(savedBooking -> {
                            Map<String, String> response = new HashMap<>();
                            response.put("message", "Ticket booking successful with pnrId: " + pnr);
                            response.put("Total amount", String.valueOf(savedBooking.getTotalAmount()));
                            return response;
                        });
                })
            );
    }
		public Mono<Booking> getTicketsByPnr(String pnr){
			return bookingRepo.findByPnr(pnr);
		}
		public Mono<Booking> getBookingsByemailId(String emailId){
			return bookingRepo.findByEmail(emailId);
		}
		public Mono<Void> deleteBooking(String pnr){
			return bookingRepo.findByPnr(pnr).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"No booking to delete"))).flatMap(bookingRepo::delete);
		}
}
