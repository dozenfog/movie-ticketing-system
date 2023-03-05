package by.issoft.dto.out.cinema;

import by.issoft.domain.cinema.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRoomOutDTO {
    private Long id;
    private int capacity;
    private RoomType roomType;
    private String cinemaName;
    private List<SeatOutDTO> seats;
}
