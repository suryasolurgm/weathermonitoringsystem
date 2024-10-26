package dev.surya.weathermonitoringsystem.repositories;

import dev.surya.weathermonitoringsystem.models.DailySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailySummaryRepository extends JpaRepository<DailySummary, Long> {

    List<DailySummary> findByDate(LocalDate date);

    List<DailySummary> findByCity(String city);

    List<DailySummary> findByCityAndDate(String city, LocalDate date);

    List<DailySummary> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<DailySummary> findByCityAndDateBetween(String city, LocalDate startDate, LocalDate endDate);
}
