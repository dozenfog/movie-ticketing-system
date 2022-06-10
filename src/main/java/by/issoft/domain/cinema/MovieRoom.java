package by.issoft.domain.cinema;

import by.issoft.domain.AbstractEntity;
import by.issoft.domain.event.Event;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(exclude = "events")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie_room")
@SuperBuilder(toBuilder = true)
public class MovieRoom extends AbstractEntity {
    @Column(nullable = false)
    private int capacity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "movie_room_id")
    @JsonIgnore
    @Builder.Default
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "movieRoom", orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<Event> events = new ArrayList<>();
}
