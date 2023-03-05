package by.issoft.config;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AppConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final var source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        source.registerCorsConfiguration("/**", corsConfiguration.applyPermitDefaultValues());
        corsConfiguration.setExposedHeaders(List.of("Authorization", "Set-Cookie", "x-xsrf-token","Access-Control-Allow-Headers", "Origin", "Accept", "X-Requested-With",
                "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:8000"));
        corsConfiguration.setAllowedMethods(List.of("POST", "PUT", "GET", "OPTIONS", "DELETE"));
        corsConfiguration.setAllowCredentials(true);

        return source;
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.addContextCustomizers(context -> context.setCookieProcessor(new LegacyCookieProcessor()));
    }
}
