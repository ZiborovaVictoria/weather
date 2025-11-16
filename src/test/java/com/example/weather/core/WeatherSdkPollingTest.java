package com.example.weather.core;

import com.example.weather.config.WeatherSdkMode;
import com.example.weather.config.WeatherSdkProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.Instant;

import static org.mockito.Mockito.*;

class WeatherSdkPollingTest {

    private OpenWeatherClient api;
    private WeatherSdkProperties props;
    private WeatherSdkClient client;

    @BeforeEach
    void init() {
        api = Mockito.mock(OpenWeatherClient.class);

        props = new WeatherSdkProperties();
        props.setMode(WeatherSdkMode.POLLING);
        props.setCacheSize(10);
        props.setCacheTtl(Duration.ofMinutes(5));

        client = new WeatherSdkClient(api, props);
    }

    @Test
    void pollingRefreshesCachedCities() {
        WeatherResponse old = new WeatherResponse();
        old.setName("Paris");

        WeatherResponse refreshed = new WeatherResponse();
        refreshed.setName("Paris");

        client.getCache().put("paris", new CachedWeatherEntry(old, Instant.now()));

        when(api.fetchCurrentWeather("paris")).thenReturn(refreshed);

        client.refreshAllCachedCities();

        verify(api, times(1)).fetchCurrentWeather("paris");
    }
}

