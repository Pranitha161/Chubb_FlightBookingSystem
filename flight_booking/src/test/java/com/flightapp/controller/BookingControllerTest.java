package com.flightapp.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.flightapp.entity.Booking;
import com.flightapp.service.BookingService;
import com.flightapp.service.PassengerService;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private PassengerService passengerService;

    @Test
    public void testBookTicketSuccess() throws Exception {
        Booking booking = new Booking();
        booking.setEmail("hello@example.com");

        when(passengerService.getPassengerByEmail("hello@example.com"))
                .thenReturn(ResponseEntity.ok().build());

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "Ticket booking successful with pnrId: 10-2511201030");
        responseMap.put("Total amount:", " 5000");
        when(bookingService.addticket(10, booking))
                .thenReturn( ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMap));

        mockMvc.perform(post("/api/v1.0/flight/booking/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "email": "hello@example.com",
                      "seatNumbers": ["12C"],
                      "tripType": "ONE_WAY"
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Ticket booking successful with pnrId: 10-2511201030"))
                .andExpect(jsonPath("$.Total amount:").value(" 5000"));
    }

    @Test
    public void testGetByPnr() throws Exception {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("pnr", "PNR123");

        // ✅ cast to ResponseEntity<?> to avoid generic mismatch
        when(bookingService.getTicketsByPnr("PNR123"))
                .thenReturn( ResponseEntity.ok(responseMap));

        mockMvc.perform(get("/api/v1.0/flight/ticket/PNR123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pnr").value("PNR123"));
    }

    @Test
    public void testGetByEmailId() throws Exception {
        List<Map<String, String>> bookings = List.of(Map.of("email", "hello@example.com"));

        when(bookingService.getBookingsByemailId("hello@example.com"))
                .thenReturn( ResponseEntity.status(HttpStatus.ACCEPTED).body(bookings));

        mockMvc.perform(get("/api/v1.0/flight/history/hello@example.com"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$[0].email").value("hello@example.com"));
    }

    @Test
    public void testCancelBooking() throws Exception {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "Booking deleted successfully");

        when(bookingService.deleteBooking("PNR123"))
                .thenReturn(ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMap));

        mockMvc.perform(delete("/api/v1.0/flight/booking/cancel/PNR123"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value("Booking deleted successfully"));
    }
}
