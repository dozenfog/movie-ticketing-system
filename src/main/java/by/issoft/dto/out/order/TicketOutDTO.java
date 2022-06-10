package by.issoft.dto.out.order;

import by.issoft.dto.out.cinema.SeatOutDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketOutDTO {
    private BigDecimal price;
    private OrderOutDTO order;
    private SeatOutDTO seat;
}
