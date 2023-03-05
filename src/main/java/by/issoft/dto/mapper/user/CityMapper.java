package by.issoft.dto.mapper.user;

import by.issoft.domain.user.City;
import by.issoft.dto.out.user.CityOutDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CityMapper {
    CityOutDTO toDto(City city);
}
