package by.issoft.controller.admin;

import by.issoft.domain.event.Movie;
import by.issoft.domain.user.Role;
import by.issoft.dto.in.event.MovieInDTO;
import by.issoft.dto.mapper.event.MovieMapper;
import by.issoft.dto.out.ExceptionDTO;
import by.issoft.dto.out.event.MovieOutDTO;
import by.issoft.service.MovieService;
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
@RequestMapping("/admin/movies")
@RolesAllowed(Role.Fields.ADMIN)
@RequiredArgsConstructor
public class AdminMovieController {
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @Operation(summary = "Add a new movie")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Added a new movie successfully",
                    content = {
                            @Content(schema = @Schema(implementation = MovieOutDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid movie info was supplied"
            )
    })
    @PostMapping
    public MovieOutDTO addMovie(@Valid @RequestBody MovieInDTO movieInDTO) {
        Movie movie = movieMapper.fromDto(movieInDTO);
        movie = movieService.save(movie);
        return movieMapper.toDto(movie);
    }

    @Operation(summary = "Delete movie by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Deleted movie successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Movie not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @DeleteMapping("/{id}")
    public void deleteMovie(@Parameter(description = "Id of movie to be deleted")
                            @PathVariable Long id) {
        movieService.delete(id);
    }
}
