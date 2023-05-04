package by.issoft.domain.event;

import by.issoft.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(exclude = "movie")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie_image")
@SuperBuilder(toBuilder = true)
public class MovieImage extends AbstractEntity {
    @Column(unique = true, nullable = false, length = 200)
    private String url;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
