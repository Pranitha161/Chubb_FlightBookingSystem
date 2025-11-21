package com.flightapp.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class SearchRequestTest {

    @Test
    void testSearchRequestGettersAndSetters() {
        SearchRequest request = new SearchRequest();
        request.setFromPlace("Hyderabad");
        request.setToPlace("Delhi");
        request.setDate(LocalDate.of(2025, 11, 20));
        assertEquals("Hyderabad", request.getFromPlace());
        assertEquals("Delhi", request.getToPlace());
        assertEquals(LocalDate.of(2025, 11, 20), request.getDate());
    }

    @Test
    void testDefaultValues() {
        SearchRequest request = new SearchRequest();

        // By default, fields should be null
        assertNull(request.getFromPlace());
        assertNull(request.getToPlace());
        assertNull(request.getDate());
    }
}
