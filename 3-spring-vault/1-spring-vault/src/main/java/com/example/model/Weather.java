package com.example.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Weather extends WeatherEntry {

    private String name;
    private String id;

}