package by.issoft.dto.mapper.cinema;

import by.issoft.domain.cinema.Seat;
import by.issoft.dto.in.cinema.SeatInDTO;
import by.issoft.dto.out.cinema.SeatOutDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SeatMapper {
    Seat fromDto(SeatInDTO seatInDTO);

    SeatOutDTO toDto(Seat seat);
}
