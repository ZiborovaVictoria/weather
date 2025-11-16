package com.example.weather.web;

import com.example.weather.config.TestApplication;
import com.example.weather.core.WeatherResponse;
import com.example.weather.core.WeatherSdkClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = WeatherController.class)
@Import(TestApplication.class)
class WeatherControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    WeatherSdkClient client;

    @Test
    void returnsWeatherJson() throws Exception {
        WeatherResponse r = new WeatherResponse();
        WeatherResponse.WeatherPart wp = new WeatherResponse.WeatherPart();
        wp.setMain("Clear");
        r.setWeather(wp);
        r.setName("Rome");

        when(client.getCurrentWeather("Rome")).thenReturn(r);

        mvc.perform(get("/api/weather?city=Rome"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rome"))
                .andExpect(jsonPath("$.weather.main").value("Clear"));
    }
}
