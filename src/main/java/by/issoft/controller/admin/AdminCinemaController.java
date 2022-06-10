package by.issoft.controller.admin;

import by.issoft.domain.cinema.Cinema;
import by.issoft.domain.user.Role;
import by.issoft.dto.in.cinema.CinemaInDTO;
import by.issoft.dto.mapper.cinema.CinemaMapper;
import by.issoft.dto.out.ExceptionDTO;
import by.issoft.dto.out.cinema.CinemaOutDTO;
import by.issoft.service.CinemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/admin/movie-theaters")
@RolesAllowed(Role.Fields.ADMIN)
@RequiredArgsConstructor
public class AdminCinemaController {
    private final CinemaService cinemaService;
    private final CinemaMapper cinemaMapper;

    @Operation(summary = "Add a new cinema")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Added a new cinema successfully",
                    content = {
                            @Content(schema = @Schema(implementation = CinemaOutDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid cinema info was supplied"
            )
    })
    @PostMapping
    public CinemaOutDTO addCinema(@Valid @RequestBody CinemaInDTO cinemaInDTO) {
        Cinema cinema = cinemaMapper.fromDto(cinemaInDTO);
        cinema = cinemaService.save(cinema);
        return cinemaMapper.toDto(cinema);
    }

    @Operation(summary = "Delete cinema by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Deleted cinema successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cinema not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @DeleteMapping("/{id}")
    public void deleteCinema(@Parameter(description = "Id of cinema to be deleted")
                             @PathVariable Long id) {
        cinemaService.delete(id);
    }
}