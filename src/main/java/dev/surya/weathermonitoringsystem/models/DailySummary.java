package dev.surya.weathermonitoringsystem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class DailySummary {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private LocalDate date;
    private double averageTemperature;
    private double maxTemperature;
    private double minTemperature;
    private String dominantWeatherCondition;

    // Constructors
    public DailySummary() {}

    public DailySummary(String city, LocalDate date, double averageTemperature, double maxTemperature, double minTemperature, String dominantWeatherCondition) {
        this.city = city;
        this.date = date;
        this.averageTemperature = averageTemperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.dominantWeatherCondition = dominantWeatherCondition;
    }

    @Override
    public String toString() {
        return "DailySummary{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", date=" + date +
                ", averageTemperature=" + averageTemperature +
                ", maxTemperature=" + maxTemperature +
                ", minTemperature=" + minTemperature +
                ", dominantWeatherCondition='" + dominantWeatherCondition + '\'' +
                '}';
    }
}
