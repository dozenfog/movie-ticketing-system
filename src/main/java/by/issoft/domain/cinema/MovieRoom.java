package by.issoft.domain.cinema;

import by.issoft.domain.AbstractEntity;
import by.issoft.domain.event.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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
    @JsonIgnore
    private Cinema cinema;

    @OneToMany(orphanRemoval = true, mappedBy = "movieRoom")
    @Builder.Default
    //@JsonIgnore
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "movieRoom", orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<Event> events = new ArrayList<>();
}
