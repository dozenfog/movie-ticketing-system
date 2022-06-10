package by.issoft.controller;

import by.issoft.controller.utils.suppliers.OrderSupplier;
import by.issoft.domain.order.Order;
import by.issoft.domain.order.OrderStatus;
import by.issoft.domain.user.Role;
import by.issoft.exception.NotFoundException;
import by.issoft.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static by.issoft.controller.utils.constants.TestConstants.ADMIN_ORDER_MAPPING;
import static by.issoft.controller.utils.constants.TestConstants.CANCELLATION_PATH;
import static by.issoft.controller.utils.constants.TestConstants.INVALID_ID;
import static by.issoft.controller.utils.constants.TestConstants.PAYMENT_PATH;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWithIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WithMockUser(roles = {Role.Fields.ADMIN})
public class OrderControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;

    public static final String EXCEPTION_MESSAGE = "Order with id " + INVALID_ID + " was not found.";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testGetAllOrders() throws Exception {
        Order order = OrderSupplier.getOrder();
        Order savedOrder = orderRepository.save(order);

        mockMvc.perform(get(ADMIN_ORDER_MAPPING))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[1].creationDateTime").value(startsWithIgnoringCase("2022-01-08T12:30")))
                .andExpect(jsonPath("$[1].orderStatus").value(savedOrder.getOrderStatus().name()));

        orderRepository.deleteById(savedOrder.getId());
    }

    @Test
    public void getOrderByValidId() throws Exception {
        Order order = OrderSupplier.getOrder();
        Order savedOrder = orderRepository.save(order);

        mockMvc.perform(get(ADMIN_ORDER_MAPPING + savedOrder.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderStatus").value(savedOrder.getOrderStatus().name()));

        orderRepository.deleteById(savedOrder.getId());
    }

    @Test
    public void testGetOrderByInvalidId() throws Exception {
        mockMvc.perform(get(ADMIN_ORDER_MAPPING + INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(EXCEPTION_MESSAGE)))
                .andExpect(jsonPath("$.userMessage", is(EXCEPTION_MESSAGE)));
    }

    @Test
    public void testAddValidOrder() throws Exception {
        //given
        Order order = OrderSupplier.getOrder();
        int initSize = (orderRepository.findAll()).size();

        final String requestContent = OrderSupplier.getOrderJson();
        //when
        mockMvc.perform(post(ADMIN_ORDER_MAPPING)
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderStatus").value(order.getOrderStatus().name()));

        //then
        assertEquals((orderRepository.findAll()).size(), initSize + 1);
        orderRepository.delete(order);
    }


    @Test
    public void testDeleteOrderByValidId() throws Exception {
        //given
        Order order = OrderSupplier.getOrder();
        Long id = orderRepository.save(order).getId();

        //when & then
        mockMvc.perform(delete(ADMIN_ORDER_MAPPING + id))
                .andExpect(status().isOk());

        mockMvc.perform(get(ADMIN_ORDER_MAPPING + id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteOrderByInvalidId() throws Exception {
        mockMvc.perform(delete(ADMIN_ORDER_MAPPING + INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(EXCEPTION_MESSAGE)))
                .andExpect(jsonPath("$.userMessage", is(EXCEPTION_MESSAGE)));
    }

    @Test
    public void testPaymentForInvalidOrder() throws Exception {
        mockMvc.perform(put(ADMIN_ORDER_MAPPING + INVALID_ID + PAYMENT_PATH)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(EXCEPTION_MESSAGE)))
                .andExpect(jsonPath("$.userMessage", is(EXCEPTION_MESSAGE)));
    }

    @Test
    public void testPaymentForOrder() throws Exception {
        //given
        Order order = OrderSupplier.getOrder();
        Long id = orderRepository.save(order).getId();

        //when & then
        mockMvc.perform(put(ADMIN_ORDER_MAPPING + id + PAYMENT_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.creationDateTime").value(startsWithIgnoringCase("2022-01-08T12:30")))
                .andExpect(jsonPath("$.orderStatus").value(OrderStatus.ACTIVE.name()));

        orderRepository.deleteById(id);
    }

    @Test
    public void testCancelInvalidOrder() throws Exception {
        mockMvc.perform(put(ADMIN_ORDER_MAPPING + INVALID_ID + CANCELLATION_PATH)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(EXCEPTION_MESSAGE)))
                .andExpect(jsonPath("$.userMessage", is(EXCEPTION_MESSAGE)));
    }

    @Test
    public void testCancelValidOrder() throws Exception {
        //given
        Order order = OrderSupplier.getOrder();
        Long id = orderRepository.save(order).getId();

        //when & then
        mockMvc.perform(put(ADMIN_ORDER_MAPPING + id + CANCELLATION_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.creationDateTime").value(startsWithIgnoringCase("2022-01-08T12:30")))
                .andExpect(jsonPath("$.orderStatus").value(OrderStatus.CANCELLED.name()));

        orderRepository.deleteById(id);
    }
}
