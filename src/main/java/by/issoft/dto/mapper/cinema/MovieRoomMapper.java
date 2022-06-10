package by.issoft.dto.mapper.cinema;

import by.issoft.domain.cinema.MovieRoom;
import by.issoft.dto.in.cinema.MovieRoomInDTO;
import by.issoft.dto.out.cinema.MovieRoomOutDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {SeatMapper.class})
public interface MovieRoomMapper {
    @Mapping(source = "roomTypeId", target = "roomType.id")
    @Mapping(target = "events", expression = "java(new ArrayList<>())")
    MovieRoom fromDto(MovieRoomInDTO movieRoomInDTO);

    MovieRoomOutDTO toDto(MovieRoom movieRoom);
}
