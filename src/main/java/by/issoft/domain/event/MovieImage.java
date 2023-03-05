package by.issoft.domain.event;

import by.issoft.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie_image")
@SuperBuilder(toBuilder = true)
public class MovieImage extends AbstractEntity {
    @Column(unique = true, nullable = false, length = 200)
    private String url;
}
