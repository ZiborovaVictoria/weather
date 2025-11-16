package com.example.weather.core;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

public class OpenWeatherClient {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5";
    private final String apiKey;
    private final RestClient restClient;

    public OpenWeatherClient(String apiKey) {
        this(apiKey, "https://api.openweathermap.org/data/2.5");
    }

    public OpenWeatherClient(String apiKey, String baseUrl) {
        this.apiKey = apiKey;
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public WeatherResponse fetchCurrentWeather(String city) {
        try {
            OpenWeatherApiResponse api = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/weather")
                            .queryParam("q", city)
                            .queryParam("appid", apiKey)
                            .queryParam("units", "metric")
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, response) -> {
                        throw new WeatherSdkException("OpenWeather error: " + response.getStatusCode());
                    })
                    .body(OpenWeatherApiResponse.class);

            if (api == null) {
                throw new WeatherSdkException("Empty OpenWeather API response");
            }

            return map(api);

        } catch (RestClientException ex) {
            throw new WeatherSdkException("HTTP error calling OpenWeather: " + ex.getMessage(), ex);
        }
    }

    private WeatherResponse map(OpenWeatherApiResponse api) {
       WeatherResponse result = new WeatherResponse();

        // weather
        WeatherResponse.WeatherPart weatherPart = new WeatherResponse.WeatherPart();
        if (api.getWeather() != null && !api.getWeather().isEmpty()) {
            weatherPart.setMain(api.getWeather().get(0).getMain());
            weatherPart.setDescription(api.getWeather().get(0).getDescription());
        }
        result.setWeather(weatherPart);

        // temperature
        WeatherResponse.TemperaturePart tempPart = new WeatherResponse.TemperaturePart();
        if (api.getMain() != null) {
            tempPart.setTemp(api.getMain().getTemp());
            tempPart.setFeelsLike(api.getMain().getFeelsLike());
        }
        result.setTemperature(tempPart);

        result.setVisibility(api.getVisibility());

        // wind
        WeatherResponse.WindPart windPart = new WeatherResponse.WindPart();
        if (api.getWind() != null) {
            windPart.setSpeed(api.getWind().getSpeed());
        }
        result.setWind(windPart);

        result.setDatetime(api.getDatetime());

        // sys
        WeatherResponse.SysPart sysPart = new WeatherResponse.SysPart();
        if (api.getSys() != null) {
            sysPart.setSunrise(api.getSys().getSunrise());
            sysPart.setSunset(api.getSys().getSunset());
        }
        result.setSys(sysPart);

        result.setTimezone(api.getTimezone());
        result.setName(api.getName());

        return result;
    }
}
