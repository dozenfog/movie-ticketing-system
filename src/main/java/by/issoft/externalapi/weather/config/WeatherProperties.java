package by.issoft.externalapi.weather.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.weather")
@Getter
@Setter
public class WeatherProperties {
    private String apiKey;
    private String paramsUrl;
    private String url;
}
