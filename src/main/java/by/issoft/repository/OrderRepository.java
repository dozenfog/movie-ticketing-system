package by.issoft.repository;

import by.issoft.domain.order.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CommonRepository<Order> {
    List<Order> findByUserId(Long userId);

    @Modifying
    @Query("""
            update Order o set o.overallPrice = (select sum(t.price) from Ticket t where t.order.id = :id) where o.id = :id
            """)
    void updateOverallPriceById(@Param("id") Long id);

    boolean existsByIdAndUserUserName(Long orderId, String username);

    Optional<Order> findByIdAndUserUserName(Long orderId, String username);
}
