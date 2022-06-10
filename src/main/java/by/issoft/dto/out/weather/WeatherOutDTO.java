package by.issoft.dto.out.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherOutDTO {
    private Double temperature;
    private Double feelsLike;
    private Double pressure;
    private Double humidity;
    private Double minTemperature;
    private Double maxTemperature;
    private Integer visibility;
    private Double windSpeed;
    private Double windDegree;
    private Double cloudiness;
}
