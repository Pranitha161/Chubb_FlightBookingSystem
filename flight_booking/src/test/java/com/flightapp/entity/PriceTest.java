package com.flightapp.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    void testPriceGettersAndSetters() {
        Price price = new Price();

        // Set values
        price.setOneWay(5000);
        price.setRoundTrip(9000);

        // Verify values
        assertEquals(5000, price.getOneWay());
        assertEquals(9000, price.getRoundTrip());
    }

    @Test
    void testPriceDefaultValues() {
        Price price = new Price();

        // By default, fields should be 0
        assertEquals(0, price.getOneWay());
        assertEquals(0, price.getRoundTrip());
    }
}
