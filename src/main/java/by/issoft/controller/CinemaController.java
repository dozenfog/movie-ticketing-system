package by.issoft.controller;

import by.issoft.dto.mapper.cinema.CinemaMapper;
import by.issoft.dto.out.ExceptionDTO;
import by.issoft.dto.out.cinema.CinemaOutDTO;
import by.issoft.exception.NotFoundException;
import by.issoft.service.CinemaService;
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
@RequestMapping("/movie-theaters")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;
    private final CinemaMapper cinemaMapper;

    @Operation(summary = "Get a list of all cinemas")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all cinemas",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = CinemaOutDTO.class)))
                    })
    })
    @GetMapping
    public List<CinemaOutDTO> getAllCinemas() {
        return cinemaService.findAll().stream()
                .map(cinemaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get a cinema by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the cinema",
                    content = {
                            @Content(schema = @Schema(implementation = CinemaOutDTO.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cinema not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @GetMapping("/{id}")
    public CinemaOutDTO getCinemaById(@Parameter(description = "Id of cinema to be searched")
                                      @PathVariable Long id) {
        return cinemaService.findById(id)
                .map(cinemaMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Cinema with id " + id + " was not found."));
    }
}
