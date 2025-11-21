package com.flightapp.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.flightapp.entity.enums.MealPreference;
import com.flightapp.entity.enums.TripType;

public class BookingTest {

    @Test
    void testBookingGettersAndSetters() {
        Booking booking = new Booking();

        // Set values
        booking.setId(1);
        booking.setPnr("PNR123");
        booking.setEmail("hello@example.com");
        booking.setSeatCount(2);
        booking.setTripType(TripType.ONE_WAY);
        booking.setMealPreference(MealPreference.VEG);
        booking.setTotalAmount(5000);
        booking.setSeatNumbers(List.of("12C", "12D"));

        Flight flight = new Flight();
        flight.setId(10);
        flight.setFromPlace("Delhi");
        flight.setToPlace("Hyderabad");
        booking.setFlight(flight);

        Passenger passenger = new Passenger();
        passenger.setId(1);
        passenger.setEmail("hello@example.com");
        passenger.setAge(25);
        passenger.setGender("female");
        booking.setPassengers(List.of(passenger));

        // Verify values
        assertEquals(1, booking.getId());
        assertEquals("PNR123", booking.getPnr());
        assertEquals("hello@example.com", booking.getEmail());
        assertEquals(2, booking.getSeatCount());
        assertEquals(TripType.ONE_WAY, booking.getTripType());
        assertEquals(MealPreference.VEG, booking.getMealPreference());
        assertEquals(5000, booking.getTotalAmount());
        assertEquals(List.of("12C", "12D"), booking.getSeatNumbers());

        assertNotNull(booking.getFlight());
        assertEquals(10, booking.getFlight().getId());
        assertEquals("Delhi", booking.getFlight().getFromPlace());
        assertEquals("Hyderabad", booking.getFlight().getToPlace());

        assertEquals(1, booking.getPassengers().size());
        assertEquals("hello@example.com", booking.getPassengers().get(0).getEmail());
        assertEquals(25, booking.getPassengers().get(0).getAge());
        assertEquals("female", booking.getPassengers().get(0).getGender());
    }
}
