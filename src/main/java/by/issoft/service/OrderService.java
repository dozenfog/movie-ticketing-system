package by.issoft.service;

import by.issoft.domain.order.Order;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Optional;

public interface OrderService extends CommonService<Order> {
    boolean existsByIdAndUsername(Long id, String username);

    Optional<Order> findByIdAndUsername(Long orderId, String username);

    List<Order> findByUserId(Long id);

    void updateOverallPriceById(Long id);

    boolean existsById(Long id);

    Order cancel(Order order);

    Order pay(Order order);
}
