package com.flightappreactive.demo.service;
import org.springframework.stereotype.Service;

import com.flightappreactive.demo.entity.Seat;
import com.flightappreactive.demo.repository.SeatRepository;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Flux<Seat> initializeSeats(Integer flightId, int rows, int cols) {
        char[] seatLetters = {'A','B','C','D','E','F'};
        List<Seat> seats = new ArrayList<>();

        for (int row = 1; row <= rows; row++) {
            for (int col = 0; col < cols; col++) {
                Seat seat = new Seat();
                seat.setSeatNumber(row + "" + seatLetters[col]);
                seat.setAvailable(true);
                seat.setFlightId(flightId);   // foreign key instead of setFlight(this)
                seats.add(seat);
            }
        }

        // Save all seats reactively
        return seatRepository.saveAll(seats);
    }
}
