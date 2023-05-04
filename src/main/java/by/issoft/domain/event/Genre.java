package by.issoft.domain.event;

import by.issoft.domain.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(exclude = {"movies"})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "genre")
@SuperBuilder(toBuilder = true)
public class Genre extends AbstractEntity {
    @Column(unique = true, nullable = false, length = 20)
    private String name;

    @ManyToMany(mappedBy = "genres")
    @JsonIgnore
    @Builder.Default
    private List<Movie> movies = new ArrayList<>();
}
