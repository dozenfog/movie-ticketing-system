package by.issoft.controller.admin;

import by.issoft.domain.user.Role;
import by.issoft.dto.mapper.order.OrderMapper;
import by.issoft.dto.out.ExceptionDTO;
import by.issoft.dto.out.order.OrderOutDTO;
import by.issoft.service.OrderService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/orders")
@RolesAllowed(Role.Fields.ADMIN)
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Operation(summary = "Get a list of all orders")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all orders",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = OrderOutDTO.class)))
                    })
    })
    @GetMapping
    public List<OrderOutDTO> getAllOrders() {
        return orderService.findAll().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get orders by user id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found orders by user id",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = OrderOutDTO.class)))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @GetMapping("/users/{id}")
    public List<OrderOutDTO> getOrdersByUserId(@Parameter(description = "Id of user orders of whom to be searched by")
                                               @PathVariable Long id) {
        return orderService.findByUserId(id).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Operation(summary = "Delete order by its id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Deleted order successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionDTO.class))
                    }
            )
    })
    @DeleteMapping("/{id}")
    public void deleteOrder(@Parameter(description = "Id of order to be deleted")
                            @PathVariable Long id) {
        orderService.delete(id);
    }
}
