package com.example.weather.core;

import com.example.weather.config.WeatherSdkMode;
import com.example.weather.config.WeatherSdkProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class WeatherSdkClient {

    private final OpenWeatherClient apiClient;
    private final WeatherSdkProperties props;
    private final ObjectMapper mapper = new ObjectMapper();

    private final Map<String, CachedWeatherEntry> cache;

    public WeatherSdkClient(OpenWeatherClient apiClient, WeatherSdkProperties props) {
        this.apiClient = apiClient;
        this.props = props;

        this.cache = Collections.synchronizedMap(new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, CachedWeatherEntry> eldest) {
                return size() > props.getCacheSize();
            }
        });
    }

    public WeatherResponse getCurrentWeather(String city) {
        String key = city.toLowerCase();

        CachedWeatherEntry entry = cache.get(key);
        if (entry != null && !isExpired(entry)) {
            return entry.getResponse();
        }

        WeatherResponse fresh = apiClient.fetchCurrentWeather(city);
        cache.put(key, new CachedWeatherEntry(fresh, Instant.now()));

        return fresh;
    }

    public String getCurrentWeatherAsJson(String city) {
        try {
            return mapper.writeValueAsString(getCurrentWeather(city));
        } catch (JsonProcessingException e) {
            throw new WeatherSdkException("JSON serialization error", e);
        }
    }

    private boolean isExpired(CachedWeatherEntry entry) {
        Duration ttl = props.getCacheTtl();
        return Duration.between(entry.getTimestamp(), Instant.now()).compareTo(ttl) > 0;
    }

    @VisibleForTesting
    Map<String, CachedWeatherEntry> getCache() {
        return cache;
    }

    // POLLING MODE
    @Scheduled(fixedDelayString = "#{T(org.springframework.boot.convert.DurationStyle).detectAndParse('${weather.sdk.polling-interval:1m}').toMillis()}")
    public void refreshAllCachedCities() {
        if (props.getMode() != WeatherSdkMode.POLLING) {
            return;
        }

        synchronized (cache) {
            for (String city : cache.keySet()) {
                try {
                    WeatherResponse fresh = apiClient.fetchCurrentWeather(city);
                    cache.put(city, new CachedWeatherEntry(fresh, Instant.now()));
                } catch (Exception ignore) {}
            }
        }
    }
}
