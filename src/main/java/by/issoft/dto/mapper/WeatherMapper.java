package by.issoft.dto.mapper;

import by.issoft.domain.user.City;
import by.issoft.domain.weather.Weather;
import by.issoft.externalapi.weather.dto.out.WeatherApiOutDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WeatherMapper {
    @Mappings({
            @Mapping(source = "weatherApiOutDTO.weatherParamsDTO.temperature", target = "temperature"),
            @Mapping(source = "weatherApiOutDTO.weatherParamsDTO.feelsLike", target = "feelsLike"),
            @Mapping(source = "weatherApiOutDTO.weatherParamsDTO.pressure", target = "pressure"),
            @Mapping(source = "weatherApiOutDTO.weatherParamsDTO.humidity", target = "humidity"),
            @Mapping(source = "weatherApiOutDTO.weatherParamsDTO.minTemperature", target = "minTemperature"),
            @Mapping(source = "weatherApiOutDTO.weatherParamsDTO.maxTemperature", target = "maxTemperature"),
            @Mapping(source = "weatherApiOutDTO.windDTO.windSpeed", target = "windSpeed"),
            @Mapping(source = "weatherApiOutDTO.windDTO.windDegree", target = "windDegree"),
            @Mapping(source = "weatherApiOutDTO.cloudsDTO.cloudiness", target = "cloudiness"),
            @Mapping(source = "city", target = "city"),
            @Mapping(target = "id", ignore = true)
    })
    Weather fromDto(WeatherApiOutDTO weatherApiOutDTO, City city);
}
