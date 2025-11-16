package com.example.weather.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * Weather SDK configuration properties.
 */
@ConfigurationProperties(prefix = "weather.sdk")
public class WeatherSdkProperties {

    /**
     * OpenWeather API key (required).
     */
    private String apiKey;

    /**
     * SDK operating mode. ON_DEMAND by default.
     */
    private WeatherSdkMode mode = WeatherSdkMode.ON_DEMAND;

    /**
     * Maximum number of cities to keep in cache.
     */
    private int cacheSize = 10;

    /**
     * Time-to-live for cached weather data.
     */
    private Duration cacheTtl = Duration.ofMinutes(10);

    /**
     * Polling interval (only used when mode = POLLING).
     */
    private Duration pollingInterval = Duration.ofMinutes(1);

    /**
     * Flag to enable or disable the SDK.
     */
    private boolean enabled = true;


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public WeatherSdkMode getMode() {
        return mode;
    }

    public void setMode(WeatherSdkMode mode) {
        this.mode = mode;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public Duration getCacheTtl() {
        return cacheTtl;
    }

    public void setCacheTtl(Duration cacheTtl) {
        this.cacheTtl = cacheTtl;
    }

    public Duration getPollingInterval() {
        return pollingInterval;
    }

    public void setPollingInterval(Duration pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
