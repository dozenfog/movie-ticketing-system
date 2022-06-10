package by.issoft.service.impl;

import by.issoft.audit.Audit;
import by.issoft.domain.order.Order;
import by.issoft.domain.order.OrderStatus;
import by.issoft.exception.NotFoundException;
import by.issoft.repository.OrderRepository;
import by.issoft.service.EventService;
import by.issoft.service.OrderService;
import by.issoft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final EventService eventService;

    @Audit
    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Audit
    @Override
    public boolean existsByIdAndUsername(Long id, String username) {
        return orderRepository.existsByIdAndUserUserName(id, username);
    }

    @Audit
    @Override
    public Optional<Order> findByIdAndUsername(Long orderId, String username) {
        return orderRepository.findByIdAndUserUserName(orderId, username);
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public List<Order> findByUserId(Long id) {
        if (!userService.existsById(id)) {
            throw new NotFoundException("User with id " + id + " was not found.");
        }
        return orderRepository.findByUserId(id);
    }

    @Audit
    @Override
    public Order save(Order order) {
        if (!userService.existsById(order.getUser().getId())) {
            throw new NotFoundException("User with id " + order.getUser().getId() + " was not found.");
        }
        if (!eventService.existsById(order.getEvent().getId())) {
            throw new NotFoundException("User with id " + order.getEvent().getId() + " was not found.");
        }
        return orderRepository.save(order);
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Audit
    @Override
    public void updateOverallPriceById(Long id) {
        orderRepository.updateOverallPriceById(id);
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return orderRepository.existsById(id);
    }

    @Audit
    @Override
    public void delete(Long id) {
        if (!existsById(id)) {
            throw new NotFoundException("Order with id " + id + " was not found.");
        }
        orderRepository.deleteById(id);
    }

    @Audit
    @Override
    public Order cancel(Order order) {
        return update(order, OrderStatus.CANCELLED);
    }

    @Audit
    @Override
    public Order pay(Order order) {
        return update(order, OrderStatus.ACTIVE);
    }

    private Order update(Order order, OrderStatus orderStatus) {
        if (order.getOrderStatus() != OrderStatus.CREATED ||
                (orderStatus.equals(OrderStatus.CANCELLED) && order.getOrderStatus() != OrderStatus.ACTIVE &&
                        order.getOrderStatus() != OrderStatus.CREATED)) {
            throw new RuntimeException("Unable to update order of status: " + order.getOrderStatus() +
                    " with status: " + orderStatus);
        }
        order.setOrderStatus(orderStatus);
        return save(order);
    }
}
