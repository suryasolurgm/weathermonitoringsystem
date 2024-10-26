package dev.surya.weathermonitoringsystem.controllers;

import dev.surya.weathermonitoringsystem.models.DailySummary;
import dev.surya.weathermonitoringsystem.models.WeatherData;
import dev.surya.weathermonitoringsystem.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current")
    public ResponseEntity<Map<String, WeatherData>> getCurrentWeather() {
        Map<String, WeatherData> currentWeather = weatherService.getCurrentWeatherForAllCities();
        return ResponseEntity.ok(currentWeather);
    }

    @GetMapping("/daily-summary")
    public ResponseEntity<List<DailySummary>> getDailySummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<DailySummary> dailySummaries = weatherService.getDailySummaryForDate(date);
        return ResponseEntity.ok(dailySummaries);
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<String>> getActiveAlerts() {
        List<String> activeAlerts = weatherService.getActiveAlerts();
        return ResponseEntity.ok(activeAlerts);
    }

    @PostMapping("/threshold")
    public ResponseEntity<String> setAlertThreshold(
            @RequestParam String city,
            @RequestParam double temperature) {
        weatherService.setAlertThreshold(city, temperature);
        return ResponseEntity.ok("Alert threshold set successfully");
    }

    @GetMapping("/historical")
    public ResponseEntity<List<DailySummary>> getHistoricalData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DailySummary> historicalData = weatherService.getHistoricalData(startDate, endDate);
        return ResponseEntity.ok(historicalData);
    }
}
