package com.example.weather;

import com.example.weather.config.TestApplication;
import com.example.weather.core.WeatherResponse;
import com.example.weather.core.WeatherSdkClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.when;

@SpringBootTest(
        classes = TestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class StarterIntegrationTest {

    @Autowired
    WebTestClient web;

    @MockBean
    WeatherSdkClient client;

    @Test
    void controllerReturnsResponse() {
        WeatherResponse resp = new WeatherResponse();
        resp.setName("Moscow");

        WeatherResponse.WeatherPart wp = new WeatherResponse.WeatherPart();
        wp.setMain("Clouds");
        resp.setWeather(wp);

        when(client.getCurrentWeather("Moscow")).thenReturn(resp);

        web.get()
                .uri("/api/weather?city=Moscow")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Moscow")
                .jsonPath("$.weather.main").isEqualTo("Clouds");
    }
}
