package com.example.controller;

import com.example.model.Weather;
import com.example.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @RequestMapping("/{country}/{city}")
    public Weather getWeather(@PathVariable String country, @PathVariable String city) {
        return weatherService.getWeather(country, city);
    }

}
