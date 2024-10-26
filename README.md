# Real-Time Data Processing System for Weather Monitoring 🌦️

This project is a real-time data processing system that monitors and analyzes weather data for metro cities in India using the OpenWeatherMap API. It includes features for real-time data retrieval, aggregation of daily weather summaries, alert notifications based on user-defined thresholds, and data visualization for historical and current weather trends.

## Features
- **Real-Time Data Retrieval**: Collects weather data from the OpenWeatherMap API for major Indian metro cities at regular intervals.
- **Daily Summaries**: Calculates daily averages, minimums, and maximums for temperature and determines the dominant weather condition.
- **Alert Notifications**: Configurable thresholds for temperature and weather conditions to trigger alerts when specified criteria are met.
- **Data Visualization**: Displays current and historical weather data, summaries, and alerts.
- **Extendable**: Additional weather parameters such as humidity and wind speed can be incorporated into rollups and aggregates.

## Table of Contents
- [Objective](#objective)
- [Data Source](#data-source)
- [System Architecture](#system-architecture)
- [Endpoints](#endpoints)
- [Getting Started](#getting-started)
- [Running with Docker](#running-with-docker)
- [Dependencies](#dependencies)
- [Design Choices](#design-choices)
- [Testing](#testing)
- [Future Enhancements](#future-enhancements)
- [License](#license)
- [Author](#author)

## Objective
The main objective is to build a real-time data processing system to monitor weather conditions and provide summarized insights using rollups and aggregates. This system is designed to support real-time monitoring and threshold-based alerts, providing insights for each day and historical weather trends.

## Data Source
The system retrieves weather data from the OpenWeatherMap API. Sign up for a free API key at [OpenWeatherMap](https://openweathermap.org/).

### Focused Data Fields
- **Main Weather Condition** (`main`): Primary weather condition (e.g., Rain, Snow, Clear).
- **Temperature** (`temp`): Current temperature in Celsius or Fahrenheit.
- **Feels Like** (`feels_like`): Perceived temperature.
- **Timestamp** (`dt`): Unix timestamp of data update.

## System Architecture

1. **Data Ingestion**: A scheduled job fetches weather data every 5 minutes (or a configurable interval) from OpenWeatherMap for the target cities.
2. **Data Processing**:
   - Converts temperatures from Kelvin to Celsius or Fahrenheit based on user preference.
   - Aggregates daily weather data to calculate average, max, and min temperatures, and identifies the dominant weather condition.
3. **Alerting System**:
   - Users can configure thresholds for alerts (e.g., temperature above 35°C).
   - Alerts are triggered if a threshold is breached and can be displayed on the console or sent via email.
4. **Data Storage**: PostgreSQL stores historical weather data and daily summaries for reporting and future analysis.
5. **Visualization**: Visual components display current and historical weather data, daily summaries, and triggered alerts.

## Endpoints

| Endpoint                    | Method | Description                                           |
|-----------------------------|--------|-------------------------------------------------------|
| `/api/weather/current`      | GET    | Retrieves the current weather data for all cities     |
| `/api/weather/daily-summary`| GET    | Retrieves the daily weather summary for a specific date |
| `/api/weather/alerts`       | GET    | Lists active weather alerts                           |
| `/api/weather/threshold`    | POST   | Sets alert threshold for temperature and weather      |
| `/api/weather/historical`   | GET    | Retrieves historical weather data within a date range |

## Getting Started

### Prerequisites
- **Java 11 or higher**
- **Maven**
- **PostgreSQL** (or can run in a Docker container)

### Setup

1. **Configure Environment Variables**:
   - Create a `.env` file in the root directory with your OpenWeatherMap API key:
     ```plaintext
     OPENWEATHERMAP_API_KEY=your_api_key
     ```

2. **Database Setup**:
   - Ensure PostgreSQL is running.
   - Create a database (e.g., `weather_db`), and update database connection details in the application’s configuration file.

3. **Build the Application**:
   ```bash
   mvn clean install
