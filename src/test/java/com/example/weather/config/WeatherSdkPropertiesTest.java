package com.example.weather.config;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class WeatherSdkPropertiesTest {

    @Test
    void defaultValuesAreCorrect() {
        WeatherSdkProperties props = new WeatherSdkProperties();

        assertTrue(props.isEnabled());
        assertEquals(WeatherSdkMode.ON_DEMAND, props.getMode());
        assertEquals(10, props.getCacheSize());
        assertEquals(Duration.ofMinutes(10), props.getCacheTtl());
        assertEquals(Duration.ofMinutes(1), props.getPollingInterval());
    }

    @Test
    void settersWorkCorrectly() {
        WeatherSdkProperties props = new WeatherSdkProperties();

        props.setEnabled(true);
        props.setApiKey("ABC");
        props.setMode(WeatherSdkMode.POLLING);
        props.setCacheSize(5);
        props.setCacheTtl(Duration.ofMinutes(5));
        props.setPollingInterval(Duration.ofSeconds(30));

        assertTrue(props.isEnabled());
        assertEquals("ABC", props.getApiKey());
        assertEquals(WeatherSdkMode.POLLING, props.getMode());
        assertEquals(5, props.getCacheSize());
        assertEquals(Duration.ofMinutes(5), props.getCacheTtl());
        assertEquals(Duration.ofSeconds(30), props.getPollingInterval());
    }
}

