package by.issoft.service;

import by.issoft.domain.user.City;
import by.issoft.domain.weather.Weather;

public interface WeatherService {
    Weather findByCity(City city);

    void updateWeather();
}
