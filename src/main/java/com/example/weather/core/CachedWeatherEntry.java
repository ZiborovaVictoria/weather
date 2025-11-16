package com.example.weather.core;

import java.time.Instant;

public class CachedWeatherEntry {

    private final WeatherResponse response;
    private final Instant timestamp;

    public CachedWeatherEntry(WeatherResponse response, Instant timestamp) {
        this.response = response;
        this.timestamp = timestamp;
    }

    public WeatherResponse getResponse() {
        return response;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
