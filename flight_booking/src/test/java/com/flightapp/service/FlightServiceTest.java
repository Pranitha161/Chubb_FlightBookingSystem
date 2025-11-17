package com.flightapp.service;

import com.flightapp.entity.Airline;
import com.flightapp.entity.Flight;
import com.flightapp.entity.Price;
import com.flightapp.entity.SearchRequest;
import com.flightapp.repository.AirLineRepository;
import com.flightapp.repository.FlightRepository;
import com.flightapp.service.FlightService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private AirLineRepository airlineRepo;

    @Mock
    private FlightRepository flightRepo;

    @InjectMocks
    private FlightService flightService;

    @Test
    void shouldSearchFlightsByFromToAndDate() {
        // Given
        SearchRequest request = new SearchRequest();
        request.setFromPlace("Delhi");
        request.setToPlace("Mumbai");
        request.setDate(LocalDate.of(2025, 12, 25));

        Flight matchingFlight = createFlight(1, "Delhi", "Mumbai", LocalDateTime.of(2025, 12, 25, 10, 0));
        Flight wrongDateFlight = createFlight(2, "Delhi", "Mumbai", LocalDateTime.of(2025, 12, 26, 10, 0));

        when(flightRepo.getFlightByFromPlaceAndToPlace("Delhi", "Mumbai"))
                .thenReturn(Arrays.asList(matchingFlight, wrongDateFlight));

        // When
        List<Flight> result = flightService.search(request);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1);
    }

    @Test
    void shouldReturnEmptyListWhenNoFlightsMatchDate() {
        SearchRequest request = new SearchRequest();
        request.setFromPlace("Delhi");
        request.setToPlace("Mumbai");
        request.setDate(LocalDate.of(2025, 12, 25));

        when(flightRepo.getFlightByFromPlaceAndToPlace("Delhi", "Mumbai"))
                .thenReturn(List.of(createFlight(1, "Delhi", "Mumbai", LocalDateTime.of(2025, 12, 26, 10, 0))));

        List<Flight> result = flightService.search(request);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldGetAllFlights() {
        Flight f1 = createFlight(1, "Delhi", "Mumbai", LocalDateTime.now());
        Flight f2 = createFlight(2, "Mumbai", "Bangalore", LocalDateTime.now().plusDays(1));

        when(flightRepo.findAll()).thenReturn(Arrays.asList(f1, f2));

        List<Flight> result = flightService.getFlights();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(f1, f2);
    }

    @Test
    void shouldAddFlightSuccessfullyWhenAirlineExists() throws Exception {
        // Given
        Airline existingAirline = new Airline();
        setPrivateId(Airline.class, existingAirline, 5);
        existingAirline.setName("IndiGo");

        Flight inputFlight = createFlight(null, "Chennai", "Kolkata", LocalDateTime.now().plusDays(5));
        inputFlight.setAvailableSeats(180);
        inputFlight.setAirline(existingAirline);

        when(airlineRepo.findById(5)).thenReturn(Optional.of(existingAirline));
        when(flightRepo.save(any(Flight.class))).thenAnswer(invocation -> {
            Flight f = invocation.getArgument(0);
            setPrivateId(Flight.class, f, 101); // simulate generated ID
            return f;
        });

        // When
        ResponseEntity<Map<String, String>> response = flightService.addFlight(inputFlight);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().get("message")).isEqualTo("Flight inventory added successfully");
        assertThat(response.getBody().get("flightId")).isEqualTo("FL101IND");

        verify(flightRepo).save(inputFlight);
        assertThat(inputFlight.getSeats()).hasSize(180); // 180/6 = 30 rows × 6
    }

    @Test
    void shouldReturnBadRequestWhenAirlineNotFound() {
        Flight inputFlight = createFlight(null, "Goa", "Pune", LocalDateTime.now());
        Airline airline = new Airline();
        setPrivateId(Airline.class, airline, 999);
        inputFlight.setAirline(airline);

        when(airlineRepo.findById(999)).thenReturn(Optional.empty());

        ResponseEntity<Map<String, String>> response = flightService.addFlight(inputFlight);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().get("message")).isEqualTo("No airline present to add the flight");
        verify(flightRepo, never()).save(any());
    }

    // Helper methods
    private Flight createFlight(Integer id, String from, String to, LocalDateTime departure) {
        Flight f = new Flight();
        if (id != null) setPrivateId(Flight.class, f, id);
        f.setFromPlace(from);
        f.setToPlace(to);
        f.setDepartureTime(departure);
        f.setArrivalTime(departure.plusHours(2));
        f.setAvailableSeats(180);

        Price price = new Price();
        price.setOneWay(5000);
        price.setRoundTrip(9000);
        f.setPrice(price);

        Airline airline = new Airline();
        setPrivateId(Airline.class, airline, 1);
        airline.setName("IndiGo");
        f.setAirline(airline);

        return f;
    }

    // Reflection helper to set primitive int id (since it's not Integer)
    private void setPrivateId(Class<?> clazz, Object entity, int id) {
        try {
            var field = clazz.getDeclaredField("id");
            field.setAccessible(true);
            field.setInt(entity, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}