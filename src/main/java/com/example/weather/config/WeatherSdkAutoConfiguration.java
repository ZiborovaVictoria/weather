package com.example.weather.config;

import com.example.weather.core.OpenWeatherClient;
import com.example.weather.core.WeatherSdkClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@AutoConfiguration
@EnableConfigurationProperties(WeatherSdkProperties.class)
@ConditionalOnProperty(prefix = "weather.sdk", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WeatherSdkAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RestClient weatherRestClient() {
        return RestClient.builder()
                .baseUrl("https://api.openweathermap.org/data/2.5")
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenWeatherClient openWeatherClient(
            WeatherSdkProperties props,
            RestClient weatherRestClient
    ) {
        if (props.getApiKey() == null || props.getApiKey().isBlank()) {
            throw new IllegalStateException("weather.sdk.api-key must be provided");
        }
        return new OpenWeatherClient(props.getApiKey());
    }

    @Bean
    @ConditionalOnMissingBean
    public WeatherSdkClient weatherSdkClient(
            OpenWeatherClient apiClient,
            WeatherSdkProperties props
    ) {
        return new WeatherSdkClient(apiClient, props);
    }
}
