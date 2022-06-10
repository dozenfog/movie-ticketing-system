package by.issoft.externalapi.weather.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherApiOutDTO {
    @JsonProperty("main")
    private WeatherParamsDTO weatherParamsDTO;

    private Integer visibility;

    @JsonProperty("wind")
    private WindDTO windDTO;

    @JsonProperty("clouds")
    private CloudsDTO cloudsDTO;
}
