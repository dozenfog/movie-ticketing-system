package by.issoft.dto.out.cinema;

import by.issoft.domain.cinema.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRoomOutDTO {
    private int capacity;
    private RoomType roomType;
}
