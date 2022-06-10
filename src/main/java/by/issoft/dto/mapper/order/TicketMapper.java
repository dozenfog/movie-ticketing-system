package by.issoft.dto.mapper.order;

import by.issoft.domain.order.Ticket;
import by.issoft.dto.in.order.TicketInDTO;
import by.issoft.dto.mapper.cinema.SeatMapper;
import by.issoft.dto.out.order.TicketOutDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {SeatMapper.class, OrderMapper.class})
public interface TicketMapper {
    @Mappings({
            @Mapping(source = "ticketInDTO.seatId", target = "seat.id"),
            @Mapping(target = "price", constant = "0.0")
    })
    Ticket fromDto(TicketInDTO ticketInDTO);

    TicketOutDTO toDto(Ticket ticket);
}
