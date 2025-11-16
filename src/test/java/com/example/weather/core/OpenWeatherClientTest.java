package com.example.weather.core;

import org.junit.jupiter.api.*;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.JsonBody;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class OpenWeatherClientMockServerTest {

    private static ClientAndServer mockServer;
    private OpenWeatherClient client;

    @BeforeAll
    static void startServer() {
        mockServer = ClientAndServer.startClientAndServer(1080);
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    @BeforeEach
    void setup() {
        mockServer.reset();
        client = new OpenWeatherClient("TEST_KEY", "http://localhost:1080");
    }

    @Test
    void fetchCurrentWeather_success() {
        mockServer.when(
                request()
                        .withMethod("GET")
                        .withPath("/weather")
                        .withQueryStringParameter("q", "London")
                        .withQueryStringParameter("appid", "TEST_KEY")
        ).respond(
                response()
                        .withStatusCode(200)
                        .withBody(JsonBody.json("""
                           {
                             "name": "London",
                             "main": { "temp": 10, "feels_like": 8 },
                             "weather": [ { "main": "Clouds", "description": "broken clouds" } ],
                             "visibility": 10000,
                             "wind": { "speed": 2.1 },
                             "dt": 1500,
                             "sys": { "sunrise": 100, "sunset": 200 },
                             "timezone": 3600
                           }
                        """))
        );

        WeatherResponse result = client.fetchCurrentWeather("London");

        assertEquals("London", result.getName());
        assertEquals("Clouds", result.getWeather().getMain());
        assertEquals(10, result.getTemperature().getTemp());
    }

    @Test
    void fetchCurrentWeather_unauthorized() {
        mockServer.when(
                request()
                        .withMethod("GET")
                        .withPath("/weather")
        ).respond(
                response()
                        .withStatusCode(401)
                        .withBody("{\"message\":\"Invalid API key\"}")
        );

        assertThrows(WeatherSdkException.class,
                () -> client.fetchCurrentWeather("London"));
    }
}
