package com.flightapp.controller;

import com.flightapp.entity.Airline;
import com.flightapp.entity.Flight;
import com.flightapp.entity.Price;
import com.flightapp.service.AirlineService;
import com.flightapp.service.FlightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AirLineController.class)
class AirLineControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FlightService flightService;

    @MockBean
    AirlineService airlineService;

    @Test
    void shouldAddFlightInventorySuccessfully() throws Exception {
        // Prepare flight with airline
        Flight flight = new Flight();
        Airline airline = new Airline();
        airline.setId(5);
        airline.setName("IndiGo");
        flight.setAirline(airline);
        flight.setAvailableSeats(180);
        flight.setFromPlace("Delhi");
        flight.setToPlace("Mumbai");

        Price price = new Price();
        price.setOneWay(5000);
        flight.setPrice(price);

        when(flightService.addFlight(any(Flight.class)))
                .thenReturn(ResponseEntity.status(201)
                        .body(Map.of(
                                "message", "Flight inventory added successfully",
                                "flightId", "FL10IND"
                        )));

        mockMvc.perform(post("/api/v1.0/flight/airline/inventory/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "fromPlace": "Delhi",
                                    "toPlace": "Mumbai",
                                    "availableSeats": 180,
                                    "price": {"oneWay": 5000},
                                    "airline": {"id": 5, "name": "IndiGo"}
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Flight inventory added successfully"))
                .andExpect(jsonPath("$.flightId").value("FL10IND"));
    }

    @Test
    void shouldAddAirlineSuccessfully() throws Exception {
        mockMvc.perform(post("/api/v1.0/flight/airline/add/airline").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "SpiceJet",
                                    "logoUrl": "https://spicejet.com/logo.png"
                                }
                                """))
                .andExpect(status().isOk()); 
    }
}