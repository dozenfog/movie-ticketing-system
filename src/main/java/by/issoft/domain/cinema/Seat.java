package by.issoft.domain.cinema;

import by.issoft.domain.AbstractEntity;
import by.issoft.domain.order.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(exclude = {"movieRoom", "tickets"})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seat")
@SuperBuilder(toBuilder = true)
public class Seat extends AbstractEntity {
    @Column(name = "row_number", nullable = false)
    private int rowNumber;

    @Column(name = "place_number", nullable = false)
    private int placeNumber;

    @ManyToOne
    @JoinColumn(name = "movie_room_id")
    @JsonIgnore
    private MovieRoom movieRoom;

    @OneToMany(mappedBy = "seat")
    @JsonIgnore
    @Builder.Default
    private List<Ticket> tickets = new ArrayList<>();
}
