package com.example.weather.config;

import com.example.weather.core.OpenWeatherClient;
import com.example.weather.core.WeatherSdkClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherSdkAutoConfigurationTest {

    private final ApplicationContextRunner ctx = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(WeatherSdkAutoConfiguration.class))
            .withPropertyValues(
                    "weather.sdk.enabled=true",
                    "weather.sdk.api-key=TEST123",
                    "weather.sdk.mode=ON_DEMAND",
                    "weather.sdk.cache-size=5",
                    "weather.sdk.cache-ttl=2m"
            );

    @Test
    void autoConfigCreatesBeansWhenEnabled() {
        ctx.run(context -> {
            assertThat(context).hasSingleBean(WeatherSdkClient.class);
            assertThat(context).hasSingleBean(OpenWeatherClient.class);
            assertThat(context).hasSingleBean(WeatherSdkProperties.class);
        });
    }

    @Test
    void propertiesAreBoundCorrectly() {
        ctx.run(context -> {
            WeatherSdkProperties props = context.getBean(WeatherSdkProperties.class);

            assertThat(props.isEnabled()).isTrue();
            assertThat(props.getApiKey()).isEqualTo("TEST123");
            assertThat(props.getCacheSize()).isEqualTo(5);
            assertThat(props.getCacheTtl()).isEqualTo(Duration.ofMinutes(2));
        });
    }
}

