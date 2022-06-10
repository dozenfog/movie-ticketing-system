package by.issoft.dto.out.event;

import by.issoft.domain.event.AgeRating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieOutDTO {
    private String name;
    private String description;
    private AgeRating ageRating;
    private double rating;
    private int durationInMinutes;
    private List<GenreOutDTO> genres;
}
