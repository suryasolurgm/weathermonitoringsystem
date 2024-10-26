package dev.surya.weathermonitoringsystem.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class WeatherData {

    // Getters and Setters
    private String city;
    private String mainCondition;
    private double temperature;
    private double feelsLike;
    private LocalDateTime timestamp;

    // Constructors
    public WeatherData() {}

    public WeatherData(String city, String mainCondition, double temperature, double feelsLike, LocalDateTime timestamp) {
        this.city = city;
        this.mainCondition = mainCondition;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "city='" + city + '\'' +
                ", mainCondition='" + mainCondition + '\'' +
                ", temperature=" + temperature +
                ", feelsLike=" + feelsLike +
                ", timestamp=" + timestamp +
                '}';
    }
}
