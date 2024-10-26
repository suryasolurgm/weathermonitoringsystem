package dev.surya.weathermonitoringsystem.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class WeatherUtils {

    private WeatherUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts temperature from Kelvin to Celsius.
     * @param kelvin Temperature in Kelvin
     * @return Temperature in Celsius
     */
    public static double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    /**
     * Converts temperature from Celsius to Fahrenheit.
     * @param celsius Temperature in Celsius
     * @return Temperature in Fahrenheit
     */
    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9/5) + 32;
    }

    /**
     * Converts temperature from Fahrenheit to Celsius.
     * @param fahrenheit Temperature in Fahrenheit
     * @return Temperature in Celsius
     */
    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5/9;
    }

    /**
     * Rounds a temperature to one decimal place.
     * @param temperature Temperature to round
     * @return Rounded temperature
     */
    public static double roundTemperature(double temperature) {
        return Math.round(temperature * 10.0) / 10.0;
    }

    /**
     * Converts a Unix timestamp to LocalDateTime.
     * @param unixTimestamp Unix timestamp in seconds
     * @return LocalDateTime object
     */
    public static LocalDateTime unixTimestampToLocalDateTime(long unixTimestamp) {
        return LocalDateTime.ofEpochSecond(unixTimestamp, 0, ZoneOffset.UTC);
    }

    /**
     * Checks if a given LocalDateTime is within the specified date.
     * @param dateTime LocalDateTime to check
     * @param date Date to check against
     * @return true if the dateTime is within the date, false otherwise
     */
    public static boolean isDateTimeWithinDate(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }

    /**
     * Calculates the average of an array of temperatures.
     * @param temperatures Array of temperatures
     * @return Average temperature
     */
    public static double calculateAverageTemperature(double[] temperatures) {
        if (temperatures == null || temperatures.length == 0) {
            throw new IllegalArgumentException("Temperature array must not be null or empty");
        }
        double sum = 0;
        for (double temp : temperatures) {
            sum += temp;
        }
        return sum / temperatures.length;
    }

    /**
     * Finds the maximum temperature in an array of temperatures.
     * @param temperatures Array of temperatures
     * @return Maximum temperature
     */
    public static double findMaxTemperature(double[] temperatures) {
        if (temperatures == null || temperatures.length == 0) {
            throw new IllegalArgumentException("Temperature array must not be null or empty");
        }
        double max = temperatures[0];
        for (double temp : temperatures) {
            if (temp > max) {
                max = temp;
            }
        }
        return max;
    }

    /**
     * Finds the minimum temperature in an array of temperatures.
     * @param temperatures Array of temperatures
     * @return Minimum temperature
     */
    public static double findMinTemperature(double[] temperatures) {
        if (temperatures == null || temperatures.length == 0) {
            throw new IllegalArgumentException("Temperature array must not be null or empty");
        }
        double min = temperatures[0];
        for (double temp : temperatures) {
            if (temp < min) {
                min = temp;
            }
        }
        return min;
    }
}
