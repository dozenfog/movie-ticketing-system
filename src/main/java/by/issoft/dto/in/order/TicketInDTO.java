package by.issoft.dto.in.order;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Data
@Builder
public class TicketInDTO {
    @NotNull
    @Positive
    private Long seatId;
}
