package by.issoft.controller.admin;

import by.issoft.domain.user.Role;
import by.issoft.dto.in.user.UserUpdateInDTO;
import by.issoft.dto.mapper.user.UserMapper;
import by.issoft.dto.out.ExceptionDTO;
import by.issoft.dto.out.user.UserOutDTO;
import by.issoft.exception.NotFoundException;
import by.issoft.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/users")
@RolesAllowed(Role.Fields.ADMIN)
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Get a list of all users")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all users",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = UserOutDTO.class)))
                    })
    })
    @GetMapping
    public List<UserOutDTO> getAllUsers() {
        return userService.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get a user by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the user",
                    content = {
                            @Content(schema = @Schema(implementation = UserOutDTO.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @GetMapping("/{id}")
    public UserOutDTO getUserById(@Parameter(description = "Id of user to be searched")
                                  @PathVariable Long id) {
        return userService.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " was not found."));
    }

    @Operation(summary = "Edit user info by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User info was edited",
                    content = {
                            @Content(schema = @Schema(implementation = UserOutDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user info was supplied"
            )
    })
    @PutMapping("/{id}")
    public UserOutDTO updateUserById(@Parameter(description = "Id of user to be updated")
                                     @PathVariable Long id,
                                     @Valid @RequestBody UserUpdateInDTO userUpdateInDTO) {
        return userService.findById(id)
                .map(user -> {
                    userMapper.fillFromDto(userUpdateInDTO, user);
                    return userMapper.toDto(userService.save(user));
                })
                .orElseThrow(() -> new NotFoundException("User with id " + id + " was not found."));
    }

    @Operation(summary = "Delete user by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Deleted user successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @DeleteMapping("/{id}")
    public void deleteUserById(@Parameter(description = "Id of user to be deleted")
                           @PathVariable Long id) {
        userService.delete(id);
    }

    @Operation(summary = "Find the user who bought the most tickets in particular cinema during given timespan")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the most active buying user",
                    content = {
                            @Content(schema = @Schema(implementation = UserOutDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cinema not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @GetMapping("/top/{cinemaId}")
    public UserOutDTO getTopActiveByMovieRoomTickets(@Parameter(description = "Id of cinema to be searched by")
                                                     @PathVariable Long cinemaId,
                                                     @Parameter(description = "Date from which to start searching")
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                             Optional<LocalDate> startDate,
                                                     @Parameter(description = "Date until which to finish searching")
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                             Optional<LocalDate> endDate) {
        return userMapper.toDto(userService.findTopByMovieRoomTickets(cinemaId, startDate.orElse(LocalDate.EPOCH),
                endDate.orElse(LocalDate.EPOCH.plusYears(LocalDate.EPOCH.getYear()))).get(0)
        );
    }
}
