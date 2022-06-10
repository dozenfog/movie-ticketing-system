package by.issoft.externalapi.weather.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloudsDTO {
    @JsonProperty("all")
    private Double cloudiness;
}
