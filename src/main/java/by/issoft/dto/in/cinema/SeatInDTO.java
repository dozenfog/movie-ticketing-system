package by.issoft.dto.in.cinema;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Data
@Builder
public class SeatInDTO {
    @NotNull
    @Positive
    private int rowNumber;

    @NotNull
    @Positive
    private int placeNumber;
}
