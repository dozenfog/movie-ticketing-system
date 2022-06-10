package by.issoft.externalapi.weather;

import by.issoft.externalapi.weather.config.WeatherProperties;
import by.issoft.externalapi.weather.dto.out.WeatherApiOutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WeatherClient {
    @Autowired
    private WebClient webClient;
    @Autowired
    private WeatherProperties weatherProperties;

    public Mono<WeatherApiOutDTO> getWeather(String cityName) {
        return webClient.get()
                .uri(weatherProperties.getParamsUrl(), weatherProperties.getApiKey(), cityName)
                .retrieve()
                .bodyToMono(WeatherApiOutDTO.class);
    }
}
