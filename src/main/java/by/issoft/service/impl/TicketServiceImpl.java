package by.issoft.service.impl;

import by.issoft.audit.Audit;
import by.issoft.domain.order.Ticket;
import by.issoft.exception.NotFoundException;
import by.issoft.repository.TicketRepository;
import by.issoft.service.OrderService;
import by.issoft.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService {
    private final OrderService orderService;
    private final TicketRepository ticketRepository;

    @Audit
    @Override
    public Ticket save(Ticket ticket) {
        ticket.setPrice(ticketRepository.calculateTicketPrice(ticket.getOrder().getId()));
        return ticketRepository.save(ticket);
    }

    @Audit
    @Override
    public boolean existsByIdAndAndUserName(Long ticketId, String username) {
        return ticketRepository.existsByIdAndOrderUserUserName(ticketId, username);
    }

    @Audit
    @Override
    public Optional<Ticket> findByIdAndUsername(Long ticketId, String username) {
        return ticketRepository.findByIdAndOrderUserUserName(ticketId, username);
    }

    @Audit
    @Override
    public List<Ticket> addTicketsToOrder(Long orderId, List<Ticket> tickets) {
        return orderService.findById(orderId)
                .map(order -> tickets.stream().map(ticket -> {
                    ticket.setOrder(order);
                    Ticket savedTicket = save(ticket);
                    orderService.updateOverallPriceById(orderId);
                    return savedTicket;
                }).toList())
                .orElseThrow(() -> new NotFoundException("Order with id " + orderId + " was not found."));
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findByOrderId(Long id) {
        return ticketRepository.findByOrderId(id);
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }

    @Audit
    @Override
    public void delete(Long id) {
        findById(id).ifPresentOrElse(
                ticket -> {
                    ticketRepository.deleteById(id);
                    orderService.updateOverallPriceById(ticket.getOrder().getId());
                },
                () -> {
                    throw new NotFoundException("Ticket with id " + id + " was not found.");
                }
        );
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}
