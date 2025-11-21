package com.flightapp.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

public class FlightTest {

    @Test
    void testFlightGettersAndSetters() {
        Flight flight = new Flight();

        flight.setId(10);
        flight.setFromPlace("Delhi");
        flight.setToPlace("Hyderabad");
        flight.setArrivalTime(LocalDateTime.of(2025, 11, 20, 10, 30));
        flight.setDepartureTime(LocalDateTime.of(2025, 11, 20, 8, 30));
        flight.setAvailableSeats(120);

        Price price = new Price();
        price.setOneWay(5000);
        price.setRoundTrip(9000);
        flight.setPrice(price);

        Airline airline = new Airline();
        airline.setId(1);
        airline.setName("Indigo");
        flight.setAirline(airline);

        // Verify values
        assertEquals(10, flight.getId());
        assertEquals("Delhi", flight.getFromPlace());
        assertEquals("Hyderabad", flight.getToPlace());
        assertEquals(LocalDateTime.of(2025, 11, 20, 10, 30), flight.getArrivalTime());
        assertEquals(LocalDateTime.of(2025, 11, 20, 8, 30), flight.getDepartureTime());
        assertEquals(120, flight.getAvailableSeats());
        assertEquals(5000, flight.getPrice().getOneWay());
        assertEquals(9000, flight.getPrice().getRoundTrip());
        assertEquals("Indigo", flight.getAirline().getName());
    }

    @Test
    void testInitializeSeats() {
        Flight flight = new Flight();

        // Initialize 2 rows × 3 columns (A–C)
        flight.initializeSeats(2, 3);

        List<Seat> seats = flight.getSeats();

        // Verify seat count
        assertEquals(6, seats.size());
        assertEquals(6, flight.getAvailableSeats());

        // Verify seat numbers
        assertEquals("1A", seats.get(0).getSeatNumber());
        assertEquals("1B", seats.get(1).getSeatNumber());
        assertEquals("1C", seats.get(2).getSeatNumber());
        assertEquals("2A", seats.get(3).getSeatNumber());
        assertEquals("2B", seats.get(4).getSeatNumber());
        assertEquals("2C", seats.get(5).getSeatNumber());

        // Verify all seats are available and linked to flight
        assertTrue(seats.stream().allMatch(Seat::isAvailable));
        assertTrue(seats.stream().allMatch(s -> s.getFlight() == flight));
    }
}
