package by.issoft.controller;

import by.issoft.controller.utils.suppliers.TicketSupplier;
import by.issoft.domain.order.Ticket;
import by.issoft.domain.user.Role;
import by.issoft.exception.NotFoundException;
import by.issoft.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static by.issoft.controller.utils.constants.TestConstants.ADMIN_TICKET_MAPPING;
import static by.issoft.controller.utils.constants.TestConstants.ID;
import static by.issoft.controller.utils.constants.TestConstants.INVALID_ID;
import static by.issoft.controller.utils.constants.TestConstants.ORDER_PATH;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WithMockUser(roles = {Role.Fields.ADMIN, Role.Fields.USER})
public class TicketControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private TicketRepository ticketRepository;

    public static final String EXCEPTION_MESSAGE = "Ticket with id " + INVALID_ID + " was not found.";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testGetAllTickets() throws Exception {
        Ticket ticket = TicketSupplier.getTicket();
        ticket = ticketRepository.save(ticket);

        mockMvc.perform(get(ADMIN_TICKET_MAPPING))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].seat.placeNumber", is(1)))
                .andExpect(jsonPath("$[0].seat.rowNumber", is(1)));

        ticketRepository.deleteById(ticket.getId());
    }

    @Test
    public void getTicketByValidId() throws Exception {
        Ticket ticket = TicketSupplier.getTicket();
        Ticket savedTicket = ticketRepository.save(ticket);

        mockMvc.perform(get(ADMIN_TICKET_MAPPING + savedTicket.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.seat.placeNumber", is(1)))
                .andExpect(jsonPath("$.seat.rowNumber", is(1)));

        ticketRepository.deleteById(savedTicket.getId());
    }

    @Test
    public void testGetTicketByInvalidId() throws Exception {
        mockMvc.perform(get(ADMIN_TICKET_MAPPING + INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(EXCEPTION_MESSAGE)))
                .andExpect(jsonPath("$.userMessage", is(EXCEPTION_MESSAGE)));
    }

    @Test
    public void testAddNoTicketsToOrder() throws Exception {
        final String requestContent = "[]";
        //when
        mockMvc.perform(post(ADMIN_TICKET_MAPPING + ORDER_PATH + ID)
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is("Ticket list is empty!")))
                .andExpect(jsonPath("$.userMessage", is("Ticket list is empty!")));
    }

    @Test
    public void testAddValidTicketsToValidOrder() throws Exception {
        //given
        Ticket ticket = TicketSupplier.getTicket();
        int initSize = (ticketRepository.findAll()).size();

        final String requestContent = "[1]";
        //when
        mockMvc.perform(post(ADMIN_TICKET_MAPPING + ORDER_PATH + ID)
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].price", is(4.5)));

        //then
        assertEquals((ticketRepository.findAll()).size(), initSize + 1);
        ticketRepository.delete(ticket);
    }

    @Test
    public void testGetTicketByValidOrderId() throws Exception {
        Ticket ticket = TicketSupplier.getTicket();
        ticketRepository.save(ticket);

        //when
        mockMvc.perform(get(ADMIN_TICKET_MAPPING + ORDER_PATH + ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].seat.placeNumber", is(1)))
                .andExpect(jsonPath("$[0].seat.rowNumber", is(1)));
    }

    @Test
    public void testGetTicketByInvalidOrderId() throws Exception {
        mockMvc.perform(get(ADMIN_TICKET_MAPPING + ORDER_PATH + INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(OrderControllerTest.EXCEPTION_MESSAGE)))
                .andExpect(jsonPath("$.userMessage", is(OrderControllerTest.EXCEPTION_MESSAGE)));
    }

    @Test
    public void testDeleteTicketByValidId() throws Exception {
        //given
        Ticket ticket = TicketSupplier.getTicket();
        Long id = ticketRepository.save(ticket).getId();

        //when & then
        mockMvc.perform(delete(ADMIN_TICKET_MAPPING + id))
                .andExpect(status().isOk());

        mockMvc.perform(get(ADMIN_TICKET_MAPPING + id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTicketByInvalidId() throws Exception {
        mockMvc.perform(delete(ADMIN_TICKET_MAPPING + INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(EXCEPTION_MESSAGE)))
                .andExpect(jsonPath("$.userMessage", is(EXCEPTION_MESSAGE)));
    }
}
