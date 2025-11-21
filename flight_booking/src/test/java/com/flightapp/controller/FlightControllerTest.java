package com.flightapp.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.flightapp.entity.Airline;
import com.flightapp.entity.Flight;
import com.flightapp.entity.SearchRequest;
import com.flightapp.service.FlightService;

@WebMvcTest(controllers = FlightController.class)
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @Test
    public void searchFlightSuccessfullyTest() throws Exception {
        Airline airline = new Airline();
        airline.setId(1);
        airline.setName("Indigo");
        airline.setLogoUrl("http://logo.url");
        Flight flight = new Flight();
        flight.setFromPlace("Delhi");
        flight.setToPlace("Hyderabad");
        flight.setAirline(airline);
        flight.setArrivalTime(LocalDateTime.of(2025, 11, 20, 10, 30));
        when(flightService.search(any(SearchRequest.class)))
                .thenReturn(List.of(flight));
        mockMvc.perform(post("/api/v1.0/flight/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fromPlace\":\"Delhi\",\"toPlace\":\"Hyderabad\",\"date\":\"2025-11-20\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fromPlace").value("Delhi"))
                .andExpect(jsonPath("$[0].toPlace").value("Hyderabad"));
                
    }
    @Test
    public void getFlightsEmptyTest() throws Exception {
        when(flightService.getFlights()).thenReturn(List.of());
        mockMvc.perform(get("/api/v1.0/flight/get")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("no Flights with the given criteria"));
    }
    @Test
    public void getFlights() throws Exception {
        Airline airline = new Airline();
        airline.setId(1);
        airline.setName("Indigo");
        airline.setLogoUrl("http://logo.url");

        Flight flight = new Flight();
        flight.setFromPlace("Delhi");
        flight.setToPlace("Hyderabad");
        flight.setAirline(airline);
        flight.setArrivalTime(LocalDateTime.of(2025, 11, 20, 10, 30));
        flight.setDepartureTime(LocalDateTime.of(2025, 12, 20, 10, 30));

        // Stub service to return a list with one flight
        when(flightService.getFlights()).thenReturn(List.of(flight));

        mockMvc.perform(get("/api/v1.0/flight/get")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fromPlace").value("Delhi"))
                .andExpect(jsonPath("$[0].toPlace").value("Hyderabad"));
                
    }

}
