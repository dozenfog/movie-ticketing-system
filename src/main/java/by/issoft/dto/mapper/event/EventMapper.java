package by.issoft.dto.mapper.event;

import by.issoft.domain.cinema.MovieRoom;
import by.issoft.domain.event.Event;
import by.issoft.domain.event.EventStatus;
import by.issoft.dto.in.event.EventInDTO;
import by.issoft.dto.in.event.EventUpdateInDTO;
import by.issoft.dto.mapper.cinema.MovieRoomMapper;
import by.issoft.dto.out.event.EventOutDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {MovieMapper.class, MovieRoomMapper.class}, imports = EventStatus.class)
public interface EventMapper {
    @Mappings
    ({
            @Mapping(source = "movieId", target = "movie.id"),
            @Mapping(source = "movieRoomId", target = "movieRoom.id"),
            @Mapping(source = "eventStatus", target = "eventStatus", defaultExpression = "java(EventStatus.SCHEDULED)")
    })
    Event fromDto(EventInDTO eventInDTO);

    EventOutDTO toDto(Event event);

    void fillFromDto(EventUpdateInDTO eventUpdateInDTO, @MappingTarget Event event);

    @Named("roomsToEvents")
    default List<EventOutDTO> roomsToEvents(List<MovieRoom> movieRooms) {
        return movieRooms.stream()
                .flatMap(movieRoom -> movieRoom.getEvents().stream().map(this::toDto))
                .toList();
    }
}
