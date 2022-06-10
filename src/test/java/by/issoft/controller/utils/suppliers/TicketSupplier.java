package by.issoft.controller.utils.suppliers;

import by.issoft.domain.cinema.Seat;
import by.issoft.domain.order.Order;
import by.issoft.domain.order.Ticket;

import java.math.BigDecimal;

public class TicketSupplier {
    public static Ticket getTicket() {
        return Ticket.builder()
                .seat(Seat.builder().id(1L).build())
                .order(Order.builder().id(1L).build())
                .price(BigDecimal.ZERO)
                .build();
    }
}
