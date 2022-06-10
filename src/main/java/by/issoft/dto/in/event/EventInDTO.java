package by.issoft.dto.in.event;

import by.issoft.domain.event.EventStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;


@Data
@Builder
public class EventInDTO {
    @NotNull
    private LocalDateTime startDateTime;

    @Positive
    @NotNull
    private Long movieId;

    private EventStatus eventStatus;

    @Positive
    @NotNull
    private Long movieRoomId;
}
