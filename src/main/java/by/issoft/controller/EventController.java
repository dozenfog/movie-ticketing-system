package by.issoft.controller;

import by.issoft.dto.mapper.event.EventMapper;
import by.issoft.dto.out.ExceptionDTO;
import by.issoft.dto.out.event.EventOutDTO;
import by.issoft.exception.NotFoundException;
import by.issoft.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

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
}
