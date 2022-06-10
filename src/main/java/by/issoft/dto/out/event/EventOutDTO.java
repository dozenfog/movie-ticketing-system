package by.issoft.dto.out.event;

import by.issoft.domain.event.EventStatus;
import by.issoft.dto.out.cinema.MovieRoomOutDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventOutDTO {
    private LocalDateTime startDateTime;
    private MovieOutDTO movie;
    private EventStatus eventStatus;
    private MovieRoomOutDTO movieRoom;
}
