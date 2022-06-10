package by.issoft.controller.utils.suppliers;

import by.issoft.domain.event.AgeRating;
import by.issoft.domain.event.Movie;

import java.math.BigDecimal;

public class MovieSupplier {
    public static Movie getMovie() {
        return Movie.builder()
                .name("Movie 1")
                .description("Movie test 1")
                .ageRating(AgeRating.G)
                .durationInMinutes(123)
                .price(BigDecimal.ONE)
                .rating(5)
                .build();
    }

    public static String getMovieJson() {
        return "{" +
                "\"durationInMinutes\": 123," +
                "    \"name\": \"Movie 1\"," +
                "    \"description\": \"Movie test 1\"," +
                "    \"price\": 1," +
                "    \"ageRating\": 0," +
                "    \"rating\": 5," +
                "    \"genresIds\": [" +
                "       1" +
                "]" +
                "}";
    }
}
