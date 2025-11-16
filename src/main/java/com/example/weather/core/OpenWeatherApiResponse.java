package com.example.weather.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherApiResponse {

    @JsonProperty("weather")
    private List<WeatherItem> weather;

    @JsonProperty("main")
    private MainPart main;

    @JsonProperty("visibility")
    private Integer visibility;

    @JsonProperty("wind")
    private WindPart wind;

    @JsonProperty("dt")
    private Long datetime;

    @JsonProperty("sys")
    private SysPart sys;

    @JsonProperty("timezone")
    private Integer timezone;

    @JsonProperty("name")
    private String name;

    public List<WeatherItem> getWeather() {
        return weather;
    }

    public MainPart getMain() {
        return main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public WindPart getWind() {
        return wind;
    }

    public Long getDatetime() {
        return datetime;
    }

    public SysPart getSys() {
        return sys;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public String getName() {
        return name;
    }

    public void setWeather(List<WeatherItem> weather) {
        this.weather = weather;
    }

    public void setMain(MainPart main) {
        this.main = main;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public void setWind(WindPart wind) {
        this.wind = wind;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    public void setSys(SysPart sys) {
        this.sys = sys;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    public void setName(String name) {
        this.name = name;
    }

    // nested DTOs
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherItem {
        private String main;
        private String description;

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MainPart {

        private Double temp;

        @JsonProperty("feels_like")
        private Double feelsLike;

        public Double getTemp() {
            return temp;
        }

        public Double getFeelsLike() {
            return feelsLike;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WindPart {
        private Double speed;

        public Double getSpeed() {
            return speed;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SysPart {
        private Long sunrise;
        private Long sunset;

        public Long getSunrise() {
            return sunrise;
        }

        public Long getSunset() {
            return sunset;
        }
    }
}
