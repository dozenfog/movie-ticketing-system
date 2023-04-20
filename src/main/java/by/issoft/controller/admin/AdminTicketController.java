package by.issoft.controller.admin;

import by.issoft.domain.user.Role;
import by.issoft.dto.mapper.order.TicketMapper;
import by.issoft.dto.out.ExceptionDTO;
import by.issoft.dto.out.order.TicketOutDTO;
import by.issoft.exception.NotFoundException;
import by.issoft.service.OrderService;
import by.issoft.service.TicketService;
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

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/tickets")
@RolesAllowed(Role.Fields.ADMIN)
@RequiredArgsConstructor
public class AdminTicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final OrderService orderService;

    @Operation(summary = "Get a list of all tickets")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all tickets",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = TicketOutDTO.class)))
                    })
    })
    @GetMapping
    public List<TicketOutDTO> getAllTickets() {
        return ticketService.findAll().stream()
                .map(ticketMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get a ticket by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the ticket",
                    content = {
                            @Content(schema = @Schema(implementation = TicketOutDTO.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ticket not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @GetMapping("/{id}")
    public TicketOutDTO getTicketById(@Parameter(description = "Id of ticket to be searched")
                                      @PathVariable Long id) {
        return ticketService.findById(id)
                .map(ticketMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Ticket with id " + id + " was not found."));
    }

    @Operation(summary = "Get tickets by order id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found tickets by order id",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = TicketOutDTO.class)))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @GetMapping("/order/{id}")
    public List<TicketOutDTO> getTicketsByOrderId(@Parameter(description = "Id of order tickets of which to be searched by")
                                                  @PathVariable Long id) {
        if (orderService.existsById(id)) {
            return ticketService.findByOrderId(id).stream()
                    .map(ticketMapper::toDto)
                    .toList();
        } else throw new NotFoundException("Order with id " + id + " was not found.");
    }
}
