package by.issoft.repository;

import by.issoft.domain.weather.Weather;

import java.util.Optional;

public interface WeatherRepository extends CommonRepository<Weather> {
    Optional<Weather> findFirstByCity_IdOrderByDatetimeDesc(Long cityId);
}
