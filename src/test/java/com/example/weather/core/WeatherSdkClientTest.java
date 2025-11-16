package com.example.weather.core;

import com.example.weather.config.WeatherSdkMode;
import com.example.weather.config.WeatherSdkProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherSdkClientTest {

    private OpenWeatherClient api;
    private WeatherSdkProperties props;
    private WeatherSdkClient client;

    @BeforeEach
    void setup() {
        api = Mockito.mock(OpenWeatherClient.class);
        props = new WeatherSdkProperties();
        props.setCacheSize(10);
        props.setCacheTtl(Duration.ofSeconds(5));
        props.setMode(WeatherSdkMode.ON_DEMAND);

        client = new WeatherSdkClient(api, props);
    }

    @Test
    void returnsFreshValueWhenNotCached() {
        WeatherResponse resp = new WeatherResponse();
        resp.setName("Berlin");

        when(api.fetchCurrentWeather("Berlin")).thenReturn(resp);

        WeatherResponse result = client.getCurrentWeather("Berlin");

        assertEquals("Berlin", result.getName());
        verify(api, times(1)).fetchCurrentWeather("Berlin");
    }

    @Test
    void returnsCachedValueWhenNotExpired() {
        WeatherResponse resp = new WeatherResponse();
        resp.setName("Rome");

        when(api.fetchCurrentWeather("Rome")).thenReturn(resp);

        client.getCurrentWeather("Rome");
        client.getCurrentWeather("Rome");

        verify(api, times(1)).fetchCurrentWeather("Rome");
    }

    @Test
    void serializesJsonCorrectly() {
        WeatherResponse resp = new WeatherResponse();
        resp.setName("Madrid");

        when(api.fetchCurrentWeather("Madrid")).thenReturn(resp);

        String json = client.getCurrentWeatherAsJson("Madrid");

        assertTrue(json.contains("Madrid"));
    }
}

