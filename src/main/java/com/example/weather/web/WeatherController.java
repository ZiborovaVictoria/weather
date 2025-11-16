package com.example.weather.web;

import com.example.weather.core.WeatherResponse;
import com.example.weather.core.WeatherSdkClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherSdkClient client;

    public WeatherController(WeatherSdkClient client) {
        this.client = client;
    }

    @GetMapping
    public ResponseEntity<WeatherResponse> get(@RequestParam("city") String city) {
        return ResponseEntity.ok(client.getCurrentWeather(city));
    }

    @GetMapping("/json")
    public ResponseEntity<String> getJson(@RequestParam("city") String city) {
        return ResponseEntity.ok(client.getCurrentWeatherAsJson(city));
    }
}
