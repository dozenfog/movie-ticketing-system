package by.issoft.controller;

import by.issoft.controller.utils.suppliers.CinemaSupplier;
import by.issoft.domain.cinema.Cinema;
import by.issoft.domain.user.Role;
import by.issoft.exception.NotFoundException;
import by.issoft.service.CinemaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static by.issoft.controller.utils.constants.TestConstants.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CinemaControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private CinemaService cinemaService;

    public static final String EXCEPTION_MESSAGE = "Cinema with id " + INVALID_ID + " was not found.";

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2")
            .withReuse(true)
            .withPassword("admin")
            .withUsername("admin")
            .withDatabaseName("ticketing-system-test")
            .withInitScript("data.sql");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testGetAllCinemas() throws Exception {
        Cinema cinema = CinemaSupplier.getCinema();
        Cinema savedCinema = cinemaService.save(cinema);

        mockMvc.perform(get(PUBLIC_CINEMA_MAPPING))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email").value("test@test"))
                .andExpect(jsonPath("$[1].email").value(savedCinema.getEmail()));

        cinemaService.delete(savedCinema.getId());
    }

    @Test
    public void getCinemaByValidId() throws Exception {
        Cinema cinema = CinemaSupplier.getCinema();
        Cinema savedCinema = cinemaService.save(cinema);

        mockMvc.perform(get(PUBLIC_CINEMA_MAPPING + savedCinema.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(savedCinema.getEmail()));

        cinemaService.delete(savedCinema.getId());
    }

    @Test
    public void testGetCinemaByInvalidId() throws Exception {
        mockMvc.perform(get(PUBLIC_CINEMA_MAPPING + INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(EXCEPTION_MESSAGE)))
                .andExpect(jsonPath("$.userMessage", is(EXCEPTION_MESSAGE)));
    }

    @WithMockUser(roles = {Role.Fields.ADMIN})
    @Test
    public void testAddValidCinema() throws Exception {
        //given
        Cinema cinema = CinemaSupplier.getCinema();
        int initSize = cinemaService.findAll().size();

        final String requestContent = CinemaSupplier.getCinemaJson();
        //when
        mockMvc.perform(post(ADMIN_CINEMA_MAPPING)
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(cinema.getEmail()))
                .andExpect(jsonPath("$.name").value(cinema.getName()));

        //then
        assertEquals(cinemaService.findAll().size(), initSize + 1);
    }

    @WithMockUser(roles = {Role.Fields.ADMIN})
    @Test
    public void testDeleteCinemaByValidId() throws Exception {
        //given
        Cinema cinema = CinemaSupplier.getCinema();
        Long id = cinemaService.save(cinema).getId();

        //when & then
        mockMvc.perform(delete(ADMIN_CINEMA_MAPPING + id))
                .andExpect(status().isOk());

        mockMvc.perform(get(PUBLIC_CINEMA_MAPPING + id))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = {Role.Fields.ADMIN})
    @Test
    public void testDeleteCinemaByInvalidId() throws Exception {
        mockMvc.perform(delete(ADMIN_CINEMA_MAPPING + INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(EXCEPTION_MESSAGE)))
                .andExpect(jsonPath("$.userMessage", is(EXCEPTION_MESSAGE)));
    }
}
