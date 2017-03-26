package com.example.service;

import com.example.model.Weather;

public interface WeatherService {

    Weather getWeather(String country, String city);

}