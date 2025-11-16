package com.example.weather.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherResponse {

    @JsonProperty("weather")
    private WeatherPart weather;

    @JsonProperty("temperature")
    private TemperaturePart temperature;

    @JsonProperty("visibility")
    private Integer visibility;

    @JsonProperty("wind")
    private WindPart wind;

    @JsonProperty("datetime")
    private Long datetime;

    @JsonProperty("sys")
    private SysPart sys;

    @JsonProperty("timezone")
    private Integer timezone;

    @JsonProperty("name")
    private String name;


    public WeatherPart getWeather() {
        return weather;
    }

    public void setWeather(WeatherPart weather) {
        this.weather = weather;
    }

    public TemperaturePart getTemperature() {
        return temperature;
    }

    public void setTemperature(TemperaturePart temperature) {
        this.temperature = temperature;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public WindPart getWind() {
        return wind;
    }

    public void setWind(WindPart wind) {
        this.wind = wind;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    public SysPart getSys() {
        return sys;
    }

    public void setSys(SysPart sys) {
        this.sys = sys;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static class WeatherPart {
        private String main;
        private String description;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class TemperaturePart {
        private Double temp;

        @JsonProperty("feels_like")
        private Double feelsLike;

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        public Double getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(Double feelsLike) {
            this.feelsLike = feelsLike;
        }
    }

    public static class WindPart {
        private Double speed;

        public Double getSpeed() {
            return speed;
        }

        public void setSpeed(Double speed) {
            this.speed = speed;
        }
    }

    public static class SysPart {
        private Long sunrise;
        private Long sunset;

        public Long getSunrise() {
            return sunrise;
        }

        public void setSunrise(Long sunrise) {
            this.sunrise = sunrise;
        }

        public Long getSunset() {
            return sunset;
        }

        public void setSunset(Long sunset) {
            this.sunset = sunset;
        }
    }
}
