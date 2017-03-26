package com.example.client;


import com.example.model.Weather;
import com.example.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Service
public class OpenWeatherMapClient implements WeatherService {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/{action}?q={city},{country}&APPID={key}&units=metric&lang=de";
    private static final String WEATHER_ACTION = "weather";

    private static final Logger LOG = LoggerFactory.getLogger(OpenWeatherMapClient.class);

    private RestTemplate restTemplate;
    private Environment env;

    @Autowired
    public OpenWeatherMapClient(RestTemplate restTemplate, Environment env) {
        this.restTemplate = restTemplate;
        this.env = env;
    }

    @Override
    public Weather getWeather(String country, String city) {
        LOG.info("Requesting current weather conditions for {}, {}", city, country);
        URI uri = new UriTemplate(BASE_URL).expand(WEATHER_ACTION, city, country, getApiKey());
        return invoke(uri, Weather.class);
    }

    private <T> T invoke(URI uri, Class<T> responseType) {
        RequestEntity<?> req = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON_UTF8).build();
        ResponseEntity<T> resp = restTemplate.exchange(req, responseType);
        return resp.getBody();
    }

    private String getApiKey() {
        return env.getProperty("openweather.key");
    }

}