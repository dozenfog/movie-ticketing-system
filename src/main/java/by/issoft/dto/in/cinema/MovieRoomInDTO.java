package by.issoft.dto.in.cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;


@Data
@Builder
public class MovieRoomInDTO {
    @Positive
    @NotNull
    private int capacity;

    @NotNull
    @Positive
    private Long roomTypeId;

    @NotNull
    @NotEmpty
    @JsonProperty
    private List<SeatInDTO> seats;
}
