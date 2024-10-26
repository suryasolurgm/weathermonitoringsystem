package dev.surya.weathermonitoringsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class OpenWeatherMapConfig {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    @Value("${weather.update.interval}")
    private int updateInterval;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Map<String, Map<String, Double>> monitoredCities() {
        Map<String, Map<String, Double>> cities = new HashMap<>();
        cities.put("Delhi", Map.of("lat", 28.6139, "lon", 77.2090));
        cities.put("Mumbai", Map.of("lat", 19.0760, "lon", 72.8777));
        cities.put("Chennai", Map.of("lat", 13.0827, "lon", 80.2707));
        cities.put("Bangalore", Map.of("lat", 12.9716, "lon", 77.5946));
        cities.put("Kolkata", Map.of("lat", 22.5726, "lon", 88.3639));
        cities.put("Hyderabad", Map.of("lat", 17.3850, "lon", 78.4867));
        return cities;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public int getUpdateInterval() {
        return updateInterval;
    }

    public Map<String, Map<String, Double>> getMonitoredCities() {
        return monitoredCities();
    }
}