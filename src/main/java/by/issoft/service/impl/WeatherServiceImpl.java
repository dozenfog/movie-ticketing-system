package by.issoft.service.impl;

import by.issoft.audit.Audit;
import by.issoft.domain.user.City;
import by.issoft.domain.weather.Weather;
import by.issoft.dto.mapper.WeatherMapper;
import by.issoft.exception.NotFoundException;
import by.issoft.externalapi.weather.WeatherClient;
import by.issoft.repository.CityRepository;
import by.issoft.repository.WeatherRepository;
import by.issoft.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional
public class WeatherServiceImpl implements WeatherService {
    private final WeatherRepository weatherRepository;
    private final CityRepository cityRepository;
    private final WeatherClient weatherClient;
    private final WeatherMapper weatherMapper;

    public static final int HOUR_SPAN = 1;

    @Audit
    @Override
    public Weather findByCity(City city) {
        return weatherRepository.findFirstByCity_IdOrderByDatetimeDesc(city.getId())
                .orElseThrow(() -> new NotFoundException("Weather in city: " + city.getName() + " was not found."));
    }

    @Audit
    @Scheduled(fixedDelay = HOUR_SPAN * 60 * 60 * 1000)
    @Override
    public void updateWeather() {
        Flux.fromIterable(cityRepository.findAll())
                .flatMap(city -> weatherClient.getWeather(city.getName()).zipWith(Mono.just(city)))
                .map(weatherDtoToCity -> weatherMapper.fromDto(weatherDtoToCity.getT1(), weatherDtoToCity.getT2()))
                .subscribe(weatherRepository::save);
    }
}
