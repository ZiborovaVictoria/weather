# Weather Spring Boot Starter

## Overview

This project provides a Spring Boot Starter for integrating with the OpenWeather API.
It offers a fully auto-configured SDK that allows applications to retrieve current weather data by city name.
The starter supports two operation modes: on-demand fetching and background polling.

The SDK is designed for seamless integration into any Spring Boot application without requiring manual initialization.

---

## Features

- Retrieve current weather information by city name
- Caching mechanism for up to 10 cities
- Configurable cache time-to-live (default: 10 minutes)
- Polling mode for background automatic updates
- Spring Boot auto-configuration
- Unified JSON response structure
- Meaningful exception messages
- Optional REST controller for quick testing (`/api/weather`)

---

## Installation

Add the dependency to your Maven project:

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>weather-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

Ensure the starter is installed in your local Maven repository or available in your artifact repository.

---

## Configuration

Add the following configuration to `application.yml`:

```yaml
weather:
  sdk:
    enabled: true
    api-key: "YOUR_OPENWEATHER_API_KEY"
    mode: ON_DEMAND
    cache-size: 10
    cache-ttl: 10m
    polling-interval: 1m
```

### Configuration Parameters

| Property | Description |
|---------|-------------|
| `weather.sdk.enabled` | Enables or disables the SDK. |
| `weather.sdk.api-key` | OpenWeather API key (required). |
| `weather.sdk.mode` | Operation mode: `ON_DEMAND` or `POLLING`. |
| `weather.sdk.cache-size` | Maximum number of cached cities. |
| `weather.sdk.cache-ttl` | Cache expiration duration. |
| `weather.sdk.polling-interval` | Interval for weather updates in polling mode. |

### How to Obtain an API Key

Create an account at:
https://openweathermap.org/api  
Open the "API Keys" section and generate a new key.  
Note: New API keys may require up to two hours to become active.

---

## Usage Example

```java
@Service
public class WeatherService {

    private final WeatherSdkClient client;

    public WeatherService(WeatherSdkClient client) {
        this.client = client;
    }

    public WeatherResponse getWeather(String city) {
        return client.getCurrentWeather(city);
    }
}
```

---

## REST API (Optional)

The starter provides a REST controller for quick validation and testing.

### Retrieve weather information as a JSON object

GET /api/weather?city=London

### Retrieve weather information as a raw JSON string

GET /api/weather/json?city=London

---

## JSON Response Structure

```json
{
  "weather": {
    "main": "Clouds",
    "description": "scattered clouds"
  },
  "temperature": {
    "temp": 269.6,
    "feels_like": 267.57
  },
  "visibility": 10000,
  "wind": {
    "speed": 1.38
  },
  "datetime": 1675744800,
  "sys": {
    "sunrise": 1675751262,
    "sunset": 1675787560
  },
  "timezone": 3600,
  "name": "CityName"
}
```

---

## Project Structure

- WeatherSdkClient – high-level API for retrieving weather information
- OpenWeatherClient – low-level HTTP client responsible for calling the OpenWeather API
- WeatherResponse – normalized output model
- OpenWeatherApiResponse – raw DTO mapped from OpenWeather
- CachedWeatherEntry – cached record model
- WeatherSdkAutoConfiguration – Spring Boot auto-configuration class
- WeatherSdkProperties – configuration binding class

---

## Exceptions

All runtime errors are wrapped in WeatherSdkException.
These include HTTP failures, invalid API keys, network issues, and JSON parsing errors.

---

## Requirements

- Java 21+
- Spring Boot 3.3+
- Maven
