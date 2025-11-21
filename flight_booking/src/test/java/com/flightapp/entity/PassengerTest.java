package com.flightapp.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PassengerTest {

    @Test
    void testPassengerGettersAndSetters() {
        Passenger passenger = new Passenger();

        // Set values
        passenger.setId(1);
        passenger.setName("Pranitha");
        passenger.setGender("female");
        passenger.setAge(22);
        passenger.setEmail("hello@example.com");

        Booking booking = new Booking();
        booking.setId(100);
        booking.setPnr("PNR123");
        passenger.setBooking(booking);

        // Verify values
        assertEquals(1, passenger.getId());
        assertEquals("Pranitha", passenger.getName());
        assertEquals("female", passenger.getGender());
        assertEquals(22, passenger.getAge());
        assertEquals("hello@example.com", passenger.getEmail());

        assertNotNull(passenger.getBooking());
        assertEquals(100, passenger.getBooking().getId());
        assertEquals("PNR123", passenger.getBooking().getPnr());
    }
}
