package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
public class WeatherEntry {
    private Instant timestamp;

    private double temperature;

    private Integer weatherId;

    private String weatherIcon;

    @JsonProperty("timestamp")
    public Instant getTimestamp() {
        return this.timestamp;
    }

    @JsonSetter("dt")
    public void setTimestamp(long unixTime) {
        this.timestamp = Instant.ofEpochMilli(unixTime * 1000);
    }

    @JsonProperty("main")
    public void setMain(Map<String, Object> main) {
        setTemperature(new Double(main.get("temp").toString()));
    }

    @JsonProperty("weather")
    public void setWeather(List<Map<String, Object>> weatherEntries) {
        Map<String, Object> weather = weatherEntries.get(0);
        setWeatherId((Integer) weather.get("id"));
        setWeatherIcon((String) weather.get("icon"));
    }

}
