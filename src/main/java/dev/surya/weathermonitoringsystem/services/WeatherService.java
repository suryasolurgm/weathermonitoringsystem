package dev.surya.weathermonitoringsystem.services;

import dev.surya.weathermonitoringsystem.config.OpenWeatherMapConfig;
import dev.surya.weathermonitoringsystem.models.DailySummary;
import dev.surya.weathermonitoringsystem.models.WeatherData;
import dev.surya.weathermonitoringsystem.repositories.DailySummaryRepository;
import dev.surya.weathermonitoringsystem.utils.WeatherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final OpenWeatherMapConfig config;
    private final DailySummaryRepository dailySummaryRepository;
    private final Map<String, Double> alertThresholds = new HashMap<>();
    private final List<String> activeAlerts = new ArrayList<>();

    @Autowired
    public WeatherService(RestTemplate restTemplate, OpenWeatherMapConfig config, DailySummaryRepository dailySummaryRepository) {
        this.restTemplate = restTemplate;
        this.config = config;
        this.dailySummaryRepository = dailySummaryRepository;
    }

    @Scheduled(fixedRateString = "${weather.update.interval}000")
    public void updateWeatherData() {
        Map<String, WeatherData> currentWeather = getCurrentWeatherForAllCities();
        processDailySummaries(currentWeather);
        checkAlerts(currentWeather);
    }

    public Map<String, WeatherData> getCurrentWeatherForAllCities() {
        Map<String, WeatherData> weatherMap = new HashMap<>();
        for (Map.Entry<String, Map<String, Double>> entry : config.getMonitoredCities().entrySet()) {
            String city = entry.getKey();
            Double lat = entry.getValue().get("lat");
            Double lon = entry.getValue().get("lon");

            // Updated URL according to the API documentation
            String url = String.format("%s/data/2.5/weather?lat=%f&lon=%f&units=metric&appid=%s",
                    config.getApiUrl(), lat, lon, config.getApiKey());

            try {
                Map<String, Object> response = restTemplate.getForObject(url, Map.class);
                if (response != null) {
                    WeatherData weatherData = parseWeatherResponse(response, city);
                    weatherMap.put(city, weatherData);
                }
            } catch (Exception e) {
                System.err.println("Error fetching weather data for " + city + ": " + e.getMessage());
            }
        }
        return weatherMap;
    }

    private WeatherData parseWeatherResponse(Map<String, Object> response, String city) {
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(city);

        // Parse main weather data
        Map<String, Object> main = (Map<String, Object>) response.get("main");
        if (main != null) {
            weatherData.setTemperature(((Number) main.get("temp")).doubleValue());
            weatherData.setFeelsLike(((Number) main.get("feels_like")).doubleValue());
        }

        // Parse weather conditions
        List<Map<String, Object>> weather = (List<Map<String, Object>>) response.get("weather");
        if (weather != null && !weather.isEmpty()) {
            weatherData.setMainCondition((String) weather.get(0).get("main"));
        }

        // Parse timestamp
        Number dt = (Number) response.get("dt");
        if (dt != null) {
            weatherData.setTimestamp(WeatherUtils.unixTimestampToLocalDateTime(dt.longValue()));
        }

        return weatherData;
    }

    private void processDailySummaries(Map<String, WeatherData> currentWeather) {
        LocalDate today = LocalDate.now();
        for (WeatherData weatherData : currentWeather.values()) {
            DailySummary summary = dailySummaryRepository.findByCityAndDate(weatherData.getCity(), today)
                    .stream().findFirst().orElse(new DailySummary(weatherData.getCity(), today, 0, 0, 100, ""));

            updateDailySummary(summary, weatherData);
            dailySummaryRepository.save(summary);
        }
    }

    private void updateDailySummary(DailySummary summary, WeatherData weatherData) {
        summary.setMaxTemperature(Math.max(summary.getMaxTemperature(), weatherData.getTemperature()));
        summary.setMinTemperature(Math.min(summary.getMinTemperature(), weatherData.getTemperature()));

        // Update average temperature using weighted average
        double currentAvg = summary.getAverageTemperature();
        int count = (int) (currentAvg * 10);
        summary.setAverageTemperature(((currentAvg * count) + weatherData.getTemperature()) / (count + 1));

        // Update dominant weather condition based on frequency
        if (summary.getDominantWeatherCondition().isEmpty() ||
                summary.getDominantWeatherCondition().equals(weatherData.getMainCondition())) {
            summary.setDominantWeatherCondition(weatherData.getMainCondition());
        }
    }

    private void checkAlerts(Map<String, WeatherData> currentWeather) {
        activeAlerts.clear();
        for (Map.Entry<String, WeatherData> entry : currentWeather.entrySet()) {
            String city = entry.getKey();
            double temperature = entry.getValue().getTemperature();
            double threshold = alertThresholds.getOrDefault(city, 35.0);

            if (temperature > threshold) {
                String alert = String.format("Alert: Temperature in %s is %.1f°C, exceeding the threshold of %.1f°C",
                        city, temperature, threshold);
                activeAlerts.add(alert);
            }
        }
    }

    public List<DailySummary> getDailySummaryForDate(LocalDate date) {
        return dailySummaryRepository.findByDate(date);
    }

    public List<String> getActiveAlerts() {

        return new ArrayList<>(activeAlerts);
    }

    public void setAlertThreshold(String city, double temperature) {
        System.out.println("Setting alert threshold for " + city + " to " + temperature + "°C");
        alertThresholds.put(city, temperature);
    }

    public List<DailySummary> getHistoricalData(LocalDate startDate, LocalDate endDate) {
        return dailySummaryRepository.findByDateBetween(startDate, endDate);
    }


}