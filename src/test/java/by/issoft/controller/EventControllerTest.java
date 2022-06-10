package by.issoft.controller;

import by.issoft.controller.utils.suppliers.EventSupplier;
import by.issoft.domain.event.Event;
import by.issoft.domain.user.Role;
import by.issoft.exception.NotFoundException;
import by.issoft.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static by.issoft.controller.utils.constants.TestConstants.ADMIN_EVENT_MAPPING;
import static by.issoft.controller.utils.constants.TestConstants.INVALID_ID;
import static by.issoft.controller.utils.constants.TestConstants.PUBLIC_EVENT_MAPPING;
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
public class EventControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private EventRepository eventRepository;

    public static final String EXCEPTION_MESSAGE = "Event with id " + INVALID_ID + " was not found.";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(roles = {Role.Fields.ADMIN})
    @Test
    public void testGetAllEvents() throws Exception {
        Event event = EventSupplier.getEvent();
        Event savedEvent = eventRepository.save(event);

        mockMvc.perform(get(ADMIN_EVENT_MAPPING))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[1].eventStatus").value(savedEvent.getEventStatus().name()));

        eventRepository.deleteById(savedEvent.getId());
    }

    @Test
    public void getEventByValidId() throws Exception {
        Event event = EventSupplier.getEvent();
        Event savedEvent = eventRepository.save(event);

        mockMvc.perform(get(PUBLIC_EVENT_MAPPING + savedEvent.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eventStatus").value(savedEvent.getEventStatus().name()));

        eventRepository.deleteById(savedEvent.getId());
    }

    @Test
    public void testGetEventByInvalidId() throws Exception {
        mockMvc.perform(get(PUBLIC_EVENT_MAPPING + INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(EXCEPTION_MESSAGE)))
                .andExpect(jsonPath("$.userMessage", is(EXCEPTION_MESSAGE)));
    }

    @WithMockUser(roles = {Role.Fields.ADMIN})
    @Test
    public void testAddValidEvent() throws Exception {
        //given
        Event event = EventSupplier.getEvent();
        int initSize = (eventRepository.findAll()).size();

        final String requestContent = EventSupplier.getEventJson();
        //when
        mockMvc.perform(post(ADMIN_EVENT_MAPPING)
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eventStatus").value(event.getEventStatus().name()));

        //then
        assertEquals((eventRepository.findAll()).size(), initSize + 1);
        eventRepository.delete(event);
    }

    @WithMockUser(roles = {Role.Fields.ADMIN})
    @Test
    public void testDeleteEventByValidId() throws Exception {
        //given
        Event event = EventSupplier.getEvent();
        Long id = eventRepository.save(event).getId();

        //when & then
        mockMvc.perform(delete(ADMIN_EVENT_MAPPING + id))
                .andExpect(status().isOk());

        mockMvc.perform(get(PUBLIC_EVENT_MAPPING + id))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = {Role.Fields.ADMIN})
    @Test
    public void testDeleteEventByInvalidId() throws Exception {
        mockMvc.perform(delete(ADMIN_EVENT_MAPPING + INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(EXCEPTION_MESSAGE)))
                .andExpect(jsonPath("$.userMessage", is(EXCEPTION_MESSAGE)));
    }

    @WithMockUser(roles = {Role.Fields.ADMIN})
    @Test
    public void testUpdateEventByInvalidId() throws Exception {
        mockMvc.perform(put(ADMIN_EVENT_MAPPING + INVALID_ID)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(EXCEPTION_MESSAGE)))
                .andExpect(jsonPath("$.userMessage", is(EXCEPTION_MESSAGE)));
    }

    @WithMockUser(roles = {Role.Fields.ADMIN})
    @Test
    public void testUpdateEvent() throws Exception {
        //given
        Event event = EventSupplier.getEvent();
        Long id = eventRepository.save(event).getId();

        final String requestContent = EventSupplier.getUpdateEventJson();
        Event updatedEvent = EventSupplier.getUpdatedEvent();

        //when & then
        mockMvc.perform(put(ADMIN_EVENT_MAPPING + id)
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.startDateTime").value(startsWithIgnoringCase("2022-01-08T12:30")))
                .andExpect(jsonPath("$.eventStatus").value(updatedEvent.getEventStatus().name()));

        eventRepository.deleteById(id);
    }
}
