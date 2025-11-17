package com.flightapp.service;

import com.flightapp.entity.Airline;
import com.flightapp.repository.AirLineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import  java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirlineServiceTest {
    @Mock
    private AirLineRepository airlineRepo;
    @InjectMocks
    private AirlineService airlineService;
    @Test
    void shouldAddAirlineSuccessfully() {
        Airline airlineToSave = new Airline();
        airlineToSave.setName("IndiGo");
        airlineToSave.setLogoUrl("https://indigo.in/logo.png");

        Airline savedAirline = new Airline();
        
        savedAirline.setName("IndiGo");
        savedAirline.setLogoUrl("https://indigo.in/logo.png");

        when(airlineRepo.save(any(Airline.class))).thenReturn(savedAirline);

        Airline result = airlineService.addAirline(airlineToSave);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("IndiGo");
        verify(airlineRepo).save(airlineToSave);
    }

    @Test
    void shouldReturnAllAirlinesWhenGetAllAirLinesCalled() {
        Airline airline1 = new Airline();
        airline1.setName("Air India");
        Airline airline2 = new Airline();
        airline2.setName("SpiceJet");
        List<Airline> mockAirlines = Arrays.asList(airline1, airline2);
        when(airlineRepo.findAll()).thenReturn(mockAirlines);
        List<Airline> result = airlineService.getAllAirLines();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(airline1, airline2);
        verify(airlineRepo).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoAirlinesExist() {
        when(airlineRepo.findAll()).thenReturn(List.of());
        List<Airline> result = airlineService.getAllAirLines();
        assertThat(result).isEmpty();
        verify(airlineRepo).findAll();
    }
}
