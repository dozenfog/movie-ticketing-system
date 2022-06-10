package by.issoft.dto.mapper.cinema;

import by.issoft.domain.cinema.Cinema;
import by.issoft.dto.in.cinema.CinemaInDTO;
import by.issoft.dto.mapper.event.EventMapper;
import by.issoft.dto.out.cinema.CinemaOutDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {MovieRoomMapper.class, EventMapper.class})
public interface CinemaMapper {

    @Mapping(source = "cityId", target = "city.id")
    Cinema fromDto(CinemaInDTO cinemaInDTO);

    @Mapping(source = "movieRooms", target = "events", qualifiedByName = "roomsToEvents")
    CinemaOutDTO toDto(Cinema cinema);
}
