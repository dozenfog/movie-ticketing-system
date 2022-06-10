package by.issoft.controller;

import by.issoft.domain.order.Order;
import by.issoft.domain.user.Role;
import by.issoft.domain.user.User;
import by.issoft.dto.in.order.OrderInDTO;
import by.issoft.dto.mapper.order.OrderMapper;
import by.issoft.dto.out.ExceptionDTO;
import by.issoft.dto.out.order.OrderOutDTO;
import by.issoft.exception.NotFoundException;
import by.issoft.service.OrderService;
import by.issoft.service.UserService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RolesAllowed(Role.Fields.USER)
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final UserService userService;

    @Operation(summary = "Get my orders")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found orders",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = OrderOutDTO.class)))
                    })
    })
    @GetMapping
    public List<OrderOutDTO> getMyOrders(Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        return orderService.findByUserId(user.getId()).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Operation(summary = "Get an order by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the order",
                    content = {
                            @Content(schema = @Schema(implementation = OrderOutDTO.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @GetMapping("/{id}")
    public OrderOutDTO getOrderById(@Parameter(description = "Id of order to be searched")
                                    @PathVariable Long id,
                                    Principal principal) {
        return orderService.findByIdAndUsername(id, principal.getName())
                .map(orderMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Order with id " + id + " was not found."));
    }

    @Operation(summary = "Add a new order")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Added a new order successfully",
                    content = {
                            @Content(schema = @Schema(implementation = OrderOutDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid order info was supplied"
            )
    })
    @PostMapping
    public OrderOutDTO addOrder(@Valid @RequestBody OrderInDTO orderInDTO) {
        Order order = orderMapper.fromDto(orderInDTO);
        order = orderService.save(order);
        return orderMapper.toDto(order);
    }

    @Operation(summary = "Pay for order by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order was paid",
                    content = {
                            @Content(schema = @Schema(implementation = OrderOutDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @PutMapping("/{id}/payment")
    public OrderOutDTO payForOrder(@Parameter(description = "Id of order to be paid for")
                                   @PathVariable Long id,
                                   Principal principal) {
        return orderService.findByIdAndUsername(id, principal.getName())
                .map(order -> orderMapper.toDto(orderService.pay(order)))
                .orElseThrow(() -> new NotFoundException("Order with id " + id + " was not found."));
    }

    @Operation(summary = "Cancel order by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order was cancelled",
                    content = {
                            @Content(schema = @Schema(implementation = OrderOutDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @PutMapping("/{id}/cancellation")
    public OrderOutDTO cancelOrder(@Parameter(description = "Id of order to be cancelled")
                                   @PathVariable Long id,
                                   Principal principal) {
        return orderService.findByIdAndUsername(id, principal.getName())
                .map(order -> orderMapper.toDto(orderService.cancel(order)))
                .orElseThrow(() -> new NotFoundException("Order with id " + id + " was not found."));
    }
}
