package by.issoft.dto.mapper.order;

import by.issoft.domain.order.Order;
import by.issoft.domain.order.OrderStatus;
import by.issoft.dto.in.order.OrderInDTO;
import by.issoft.dto.mapper.event.EventMapper;
import by.issoft.dto.mapper.user.UserMapper;
import by.issoft.dto.out.order.OrderOutDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {UserMapper.class, EventMapper.class}, imports = OrderStatus.class)
public interface OrderMapper {
    @Mappings({
            @Mapping(source = "userId", target = "user.id"),
            @Mapping(source = "eventId", target = "event.id"),
            @Mapping(source = "orderStatus", target = "orderStatus", defaultExpression = "java(OrderStatus.CREATED)"),
            @Mapping(target = "overallPrice", constant = "0.0")
    })
    Order fromDto(OrderInDTO orderInDTO);

    OrderOutDTO toDto(Order order);
}
