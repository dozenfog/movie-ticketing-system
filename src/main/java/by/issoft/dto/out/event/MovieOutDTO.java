package by.issoft.dto.out.event;

import by.issoft.domain.event.AgeRating;
import by.issoft.domain.event.MovieImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieOutDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private AgeRating ageRating;
    private double rating;
    private int durationInMinutes;
    private String imageUrl;
    private List<GenreOutDTO> genres;
    private List<MovieImage> movieImages;
}
