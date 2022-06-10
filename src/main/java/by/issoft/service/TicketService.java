package by.issoft.service;

import by.issoft.domain.order.Order;
import by.issoft.domain.order.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService extends CommonService<Ticket> {
    boolean existsByIdAndAndUserName(Long ticketId, String username);

    List<Ticket> addTicketsToOrder(Long orderId, List<Ticket> tickets);

    List<Ticket> findByOrderId(Long id);
}
