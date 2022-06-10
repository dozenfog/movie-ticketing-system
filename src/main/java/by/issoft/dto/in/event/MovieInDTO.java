package by.issoft.dto.in.event;

import by.issoft.domain.event.AgeRating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;


@Data
@Builder
public class MovieInDTO {
    @Positive
    private int durationInMinutes;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name is too long")
    private String name;

    @Size(max = 1000, message = "First name is too long")
    private String description;

    @Positive
    @NotNull
    private BigDecimal price;

    @NotNull
    private AgeRating ageRating;

    @Positive
    @Max(value = 10, message = "Movie rating should be between 0 and 10")
    private double rating;

    @NotNull
    @NotEmpty
    private List<Long> genresIds;
}
