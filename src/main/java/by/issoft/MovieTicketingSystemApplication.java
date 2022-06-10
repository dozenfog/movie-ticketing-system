package by.issoft;

import by.issoft.externalapi.weather.config.WeatherProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@EnableScheduling
@EnableConfigurationProperties(WeatherProperties.class)
public class MovieTicketingSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(MovieTicketingSystemApplication.class, args);
    }
}
