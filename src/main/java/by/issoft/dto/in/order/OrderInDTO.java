package by.issoft.dto.in.order;

import by.issoft.domain.order.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;


@Data
@Builder
public class OrderInDTO {
    @NotNull
    @PastOrPresent
    private LocalDateTime creationDateTime;

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @Positive
    private Long eventId;
    private OrderStatus orderStatus;
}
