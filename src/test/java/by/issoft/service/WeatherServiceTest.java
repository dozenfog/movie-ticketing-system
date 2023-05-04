package by.issoft.service;

import by.issoft.domain.user.City;
import by.issoft.domain.weather.Weather;
import by.issoft.exception.NotFoundException;
import by.issoft.externalapi.weather.WeatherClient;
import by.issoft.externalapi.weather.dto.out.WeatherApiOutDTO;
import by.issoft.service.utils.WeatherSupplier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.ResetMocksTestExecutionListener;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestExecutionListeners;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

@TestExecutionListeners(
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
        value = ResetMocksTestExecutionListener.class
)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class WeatherServiceTest {
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private WeatherClient weatherClient;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2")
            .withReuse(true)
            .withPassword("admin")
            .withUsername("admin")
            .withDatabaseName("ticketing-system-test")
            .withInitScript("data.sql");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Test
    public void testFindByCity() {
        Mono<WeatherApiOutDTO> mono = Mono.fromSupplier(WeatherSupplier::getWeatherDto);
        City city = City.builder().id(1L).build();
        WeatherApiOutDTO expectedWeather = WeatherSupplier.getWeatherDto();
        when(weatherClient.getWeather(any())).thenReturn(mono);

        Weather weather = weatherService.findByCity(city);

        assertAll(
                () -> assertEquals(weather.getCity().getId(), city.getId()),
                () -> assertEquals(expectedWeather.getWeatherParamsDTO().getFeelsLike(), weather.getFeelsLike()),
                () -> assertEquals(expectedWeather.getWeatherParamsDTO().getHumidity(), weather.getHumidity()),
                () -> assertEquals(expectedWeather.getWeatherParamsDTO().getMaxTemperature(), weather.getMaxTemperature()),
                () -> assertEquals(expectedWeather.getWeatherParamsDTO().getPressure(), weather.getPressure()),
                () -> assertEquals(expectedWeather.getWeatherParamsDTO().getMinTemperature(), weather.getMinTemperature()),
                () -> assertEquals(expectedWeather.getWeatherParamsDTO().getPressure(), weather.getPressure()),
                () -> assertEquals(expectedWeather.getWindDTO().getWindDegree(), weather.getWindDegree()),
                () -> assertEquals(expectedWeather.getWindDTO().getWindSpeed(), weather.getWindSpeed()),
                () -> assertEquals(expectedWeather.getCloudsDTO().getCloudiness(), weather.getCloudiness()),
                () -> assertEquals(expectedWeather.getVisibility(), weather.getVisibility())
        );
    }

    @Test
    public void testFindByInvalidCity() {
        City city = City.builder().id(333L).name("A").build();
        String message = "Weather in city: A was not found.";
        Exception exception = assertThrows(NotFoundException.class, () -> weatherService.findByCity(city));
        assertEquals(message, exception.getMessage());
    }
}
