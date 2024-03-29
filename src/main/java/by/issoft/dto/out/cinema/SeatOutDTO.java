package by.issoft.dto.out.cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatOutDTO {
    private Long id;
    private int rowNumber;
    private int placeNumber;
}
