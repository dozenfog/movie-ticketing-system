package by.issoft.controller;

import by.issoft.domain.order.Ticket;
import by.issoft.domain.user.Role;
import by.issoft.dto.in.order.TicketInDTO;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tickets")
@RolesAllowed(Role.Fields.USER)
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final OrderService orderService;

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
                                                  @PathVariable Long id,
                                                  Principal principal) {
        if (orderService.existsByIdAndUsername(id, principal.getName())) {
            return ticketService.findByOrderId(id).stream()
                    .map(ticketMapper::toDto)
                    .toList();
        } else throw new NotFoundException("Order with id " + id + " was not found.");
    }

    @Operation(summary = "Add tickets to order by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tickets were added to order",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = TicketOutDTO.class)))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid ticket info was supplied"
            )
    })
    @PostMapping("/order/{id}")
    public List<TicketOutDTO> addTicketsToOrder(@Valid @RequestBody List<TicketInDTO> ticketInDTOs,
                                                @Parameter(description = "Id of order to which tickets are added")
                                                @PathVariable Long id,
                                                Principal principal) {
        if (!ticketInDTOs.isEmpty()) {
            if (orderService.existsByIdAndUsername(id, principal.getName())) {
                List<Ticket> tickets = ticketInDTOs.stream().map(ticketMapper::fromDto).toList();
                tickets = ticketService.addTicketsToOrder(id, tickets);
                return tickets.stream().map(ticketMapper::toDto).toList();
            } else throw new NotFoundException("Order with id " + id + " was not found.");
        } else throw new NotFoundException("Ticket list is empty!");
    }

    @Operation(summary = "Delete ticket by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Deleted ticket successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ticket not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @DeleteMapping("/{id}")
    public void deleteTicket(@Parameter(description = "Id of ticket to be deleted")
                             @PathVariable Long id,
                             Principal principal) {
        if (ticketService.existsByIdAndAndUserName(id, principal.getName())) {
            ticketService.delete(id);
        } else throw new NotFoundException("Ticket with id " + id + " was not found.");
    }
}
