package by.issoft.dto.in.event;

import by.issoft.domain.event.EventStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;


@Data
@Builder
public class EventUpdateInDTO {
    private LocalDateTime startDateTime;

    private EventStatus eventStatus;

    @Positive
    private Long movieRoomId;
}
