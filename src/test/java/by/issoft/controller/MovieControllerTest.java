package by.issoft.controller;

import by.issoft.controller.utils.suppliers.MovieSupplier;
import by.issoft.domain.event.Movie;
import by.issoft.domain.user.Role;
import by.issoft.exception.NotFoundException;
import by.issoft.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static by.issoft.controller.utils.constants.TestConstants.ADMIN_MOVIE_MAPPING;
import static by.issoft.controller.utils.constants.TestConstants.INVALID_ID;
import static by.issoft.controller.utils.constants.TestConstants.PUBLIC_MOVIE_MAPPING;
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
public class MovieControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private MovieRepository movieRepository;

    public static final String EXCEPTION_MESSAGE = "Movie with id " + INVALID_ID + " was not found.";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testGetAllMovies() throws Exception {
        Movie movie = MovieSupplier.getMovie();
        Movie savedMovie = movieRepository.save(movie);

        mockMvc.perform(get(PUBLIC_MOVIE_MAPPING))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[1].name").value(savedMovie.getName()))
                .andExpect(jsonPath("$[1].ageRating").value(savedMovie.getAgeRating().name()))
                .andExpect(jsonPath("$[1].rating").value(savedMovie.getRating()));

        movieRepository.deleteById(savedMovie.getId());
    }

    @Test
    public void getMovieByValidId() throws Exception {
        Movie movie = MovieSupplier.getMovie();
        Movie savedMovie = movieRepository.save(movie);

        mockMvc.perform(get(PUBLIC_MOVIE_MAPPING + savedMovie.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(savedMovie.getName()))
                .andExpect(jsonPath("$.ageRating").value(savedMovie.getAgeRating().name()))
                .andExpect(jsonPath("$.rating").value(savedMovie.getRating()));

        movieRepository.delete(savedMovie);
    }

    @Test
    public void testGetMovieByInvalidId() throws Exception {
        mockMvc.perform(get(PUBLIC_MOVIE_MAPPING + INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(EXCEPTION_MESSAGE)))
                .andExpect(jsonPath("$.userMessage", is(EXCEPTION_MESSAGE)));
    }

    @WithMockUser(roles = {Role.Fields.ADMIN})
    @Test
    public void testAddValidMovie() throws Exception {
        //given
        Movie movie = MovieSupplier.getMovie();
        int initSize = (movieRepository.findAll()).size();

        final String requestContent = MovieSupplier.getMovieJson();
        //when
        mockMvc.perform(post(ADMIN_MOVIE_MAPPING)
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(movie.getName()))
                .andExpect(jsonPath("$.ageRating").value(movie.getAgeRating().name()))
                .andExpect(jsonPath("$.rating").value(movie.getRating()));

        //then
        assertEquals((movieRepository.findAll()).size(), initSize + 1);
        movieRepository.delete(movie);
    }

    @WithMockUser(roles = {Role.Fields.ADMIN})
    @Test
    public void testDeleteMovieByValidId() throws Exception {
        //given
        Movie movie = MovieSupplier.getMovie();
        Long id = movieRepository.save(movie).getId();

        //when & then
        mockMvc.perform(delete(ADMIN_MOVIE_MAPPING + id))
                .andExpect(status().isOk());

        mockMvc.perform(get(PUBLIC_MOVIE_MAPPING + id))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = {Role.Fields.ADMIN})
    @Test
    public void testDeleteMovieByInvalidId() throws Exception {
        mockMvc.perform(delete(ADMIN_MOVIE_MAPPING + INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(EXCEPTION_MESSAGE)))
                .andExpect(jsonPath("$.userMessage", is(EXCEPTION_MESSAGE)));
    }
}
