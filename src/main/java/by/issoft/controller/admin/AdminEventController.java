package by.issoft.controller.admin;

import by.issoft.domain.event.Event;
import by.issoft.domain.user.Role;
import by.issoft.dto.in.event.EventInDTO;
import by.issoft.dto.in.event.EventUpdateInDTO;
import by.issoft.dto.mapper.event.EventMapper;
import by.issoft.dto.out.ExceptionDTO;
import by.issoft.dto.out.event.EventOutDTO;
import by.issoft.exception.NotFoundException;
import by.issoft.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/events")
@RolesAllowed(Role.Fields.ADMIN)
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @Operation(summary = "Get a list of all events")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all events",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = EventOutDTO.class)))
                    })
    })
    @GetMapping
    public List<EventOutDTO> getAllEvents() {
        return eventService.findAll().stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get an event by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the event",
                    content = {
                            @Content(schema = @Schema(implementation = EventOutDTO.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Event not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @GetMapping("/{id}")
    public EventOutDTO getEventById(@Parameter(description = "Id of event to be searched")
                                    @PathVariable Long id) {
        return eventService.findById(id)
                .map(eventMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Event with id " + id + " was not found."));
    }

    @Operation(summary = "Edit event info by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Event info was edited",
                    content = {
                            @Content(schema = @Schema(implementation = EventOutDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Event not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid event info was supplied"
            )
    })
    @PutMapping("/{id}")
    public EventOutDTO updateEventById(@Parameter(description = "Id of event to be edited")
                                       @PathVariable Long id,
                                       @Valid @RequestBody EventUpdateInDTO eventUpdateInDTO) {
        return eventService.findById(id)
                .map(event -> {
                    eventMapper.fillFromDto(eventUpdateInDTO, event);
                    return eventMapper.toDto(eventService.save(event));
                })
                .orElseThrow(() -> new NotFoundException("Event with id " + id + " was not found."));
    }

    @Operation(summary = "Add a new event")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Added a new event successfully",
                    content = {
                            @Content(schema = @Schema(implementation = EventOutDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid event info was supplied"
            )
    })
    @PostMapping
    public EventOutDTO addEvent(@Valid @RequestBody EventInDTO eventInDTO) {
        Event event = eventMapper.fromDto(eventInDTO);
        event = eventService.save(event);
        return eventMapper.toDto(event);
    }

    @Operation(summary = "Delete event by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Deleted event successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Event not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @DeleteMapping("/{id}")
    public void deleteEvent(@Parameter(description = "Id of event to be deleted")
                            @PathVariable Long id) {
        eventService.delete(id);
    }
}
