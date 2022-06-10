package by.issoft.dto.out.order;

import by.issoft.domain.order.OrderStatus;
import by.issoft.dto.out.event.EventOutDTO;
import by.issoft.dto.out.user.UserOutDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderOutDTO {
    private LocalDateTime creationDateTime;
    private BigDecimal overallPrice;
    private UserOutDTO user;
    private OrderStatus orderStatus;
    private EventOutDTO event;
}
