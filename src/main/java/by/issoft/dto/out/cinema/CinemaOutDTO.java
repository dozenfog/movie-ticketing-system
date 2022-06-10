package by.issoft.dto.out.cinema;

import by.issoft.domain.user.City;
import by.issoft.dto.out.event.EventOutDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CinemaOutDTO {
    private String name;
    private String address;
    private City city;
    private String email;
    private String phone;
    private List<EventOutDTO> events;
}
