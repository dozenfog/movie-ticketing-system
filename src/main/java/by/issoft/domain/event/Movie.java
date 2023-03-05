package by.issoft.domain.event;

import by.issoft.domain.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie")
@SuperBuilder(toBuilder = true)
public class Movie extends AbstractEntity {
    @Column(name = "duration_in_minutes", nullable = false)
    private int durationInMinutes;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated
    @Column(name = "age_rating", nullable = false)
    private AgeRating ageRating;

    @Column
    private double rating;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "movies_genres_link",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    @Builder.Default
    private List<Genre> genres = new ArrayList<>();

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    @Builder.Default
    private List<MovieImage> movieImages = new ArrayList<>();
}
