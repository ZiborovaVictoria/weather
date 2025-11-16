package com.example.weather.core;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CachedWeatherEntryTest {

    @Test
    void storesValuesCorrectly() {
        WeatherResponse resp = new WeatherResponse();
        resp.setName("Paris");

        Instant now = Instant.now();

        CachedWeatherEntry entry = new CachedWeatherEntry(resp, now);

        assertEquals(resp, entry.getResponse());
        assertEquals(now, entry.getTimestamp());
    }
}

