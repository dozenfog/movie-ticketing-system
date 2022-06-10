package by.issoft.domain.cinema;

import by.issoft.domain.AbstractEntity;
import by.issoft.domain.user.City;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(exclude = {"movieRooms"})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cinema")
@SuperBuilder(toBuilder = true)
public class Cinema extends AbstractEntity {
    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 300)
    private String address;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(unique = true, nullable = false, length = 60)
    private String email;

    @Column(nullable = false, length = 21)
    private String phone;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "cinema_id")
    @JsonIgnore
    @Builder.Default
    private List<MovieRoom> movieRooms = new ArrayList<>();
}
