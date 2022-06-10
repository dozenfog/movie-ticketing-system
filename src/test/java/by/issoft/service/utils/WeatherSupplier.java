package by.issoft.service.utils;

import by.issoft.externalapi.weather.dto.out.CloudsDTO;
import by.issoft.externalapi.weather.dto.out.WeatherApiOutDTO;
import by.issoft.externalapi.weather.dto.out.WeatherParamsDTO;
import by.issoft.externalapi.weather.dto.out.WindDTO;

public class WeatherSupplier {
    public static WeatherApiOutDTO getWeatherDto() {
        return new WeatherApiOutDTO(
                new WeatherParamsDTO(1.0, 1.0, 1.0, 1.0, 1.0, 1.0),
                1,
                new WindDTO(1.0, 1.0),
                new CloudsDTO(1.0));
    }
}
