package by.issoft.controller;

import by.issoft.dto.mapper.event.MovieMapper;
import by.issoft.dto.out.ExceptionDTO;
import by.issoft.dto.out.event.MovieOutDTO;
import by.issoft.exception.NotFoundException;
import by.issoft.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @Operation(summary = "Get a list of all movies")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all movies",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = MovieOutDTO.class)))
                    })
    })
    @GetMapping
    public List<MovieOutDTO> getAllMovies() {
        return movieService.findAll().stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get a movie by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the movie",
                    content = {
                            @Content(schema = @Schema(implementation = MovieOutDTO.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Movie not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @GetMapping("/{id}")
    public MovieOutDTO getMovieById(@Parameter(description = "Id of movie to be searched")
                                    @PathVariable Long id) {
        return movieService.findById(id)
                .map(movieMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Movie with id " + id + " was not found."));
    }
}
