package by.issoft.repository;

import by.issoft.domain.order.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends CommonRepository<Ticket> {
    List<Ticket> findByOrderId(Long orderId);

    @Query("""
            select (rt.seatPrice + m.price) * (1 - cat.discountPercentage / 100)
            from Order as o
             inner join o.user as u
             inner join u.userCategory as cat
             inner join o.event as e
             inner join e.movie as m
             inner join e.movieRoom as mr
             inner join mr.roomType as rt
             where o.id = :orderId
            """)
    BigDecimal calculateTicketPrice(@Param("orderId") Long orderId);

    boolean existsByIdAndOrderUserUserName(Long ticketId, String username);
}
