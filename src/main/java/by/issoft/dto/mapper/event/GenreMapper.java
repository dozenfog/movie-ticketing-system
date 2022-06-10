package by.issoft.dto.mapper.event;

import by.issoft.domain.event.Genre;
import by.issoft.dto.out.event.GenreOutDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GenreMapper {
    GenreOutDTO toDto(Genre genre);
}
