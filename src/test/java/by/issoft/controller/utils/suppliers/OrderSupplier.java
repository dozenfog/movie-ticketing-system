package by.issoft.controller.utils.suppliers;

import by.issoft.domain.event.Event;
import by.issoft.domain.order.Order;
import by.issoft.domain.order.OrderStatus;
import by.issoft.domain.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderSupplier {
    public static Order getOrder() {
        return Order.builder()
                .creationDateTime(LocalDateTime.parse("2022-01-08T12:30:00"))
                .user(User.builder().id(1L).build())
                .event(Event.builder().id(1L).build())
                .orderStatus(OrderStatus.CREATED)
                .overallPrice(BigDecimal.ZERO)
                .build();
    }

    public static String getOrderJson() {
        return "{" +
                "\"creationDateTime\": \"2022-01-08T12:30:00\"," +
                "    \"userId\": 1," +
                "    \"eventId\": 1" +
                "}";
    }
}
