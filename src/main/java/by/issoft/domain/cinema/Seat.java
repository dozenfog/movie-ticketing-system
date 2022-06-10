package by.issoft.domain.cinema;

import by.issoft.domain.AbstractEntity;
import by.issoft.domain.order.Ticket;
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
    private MovieRoom movieRoom;

    @OneToMany(mappedBy = "seat")
    @JsonIgnore
    @Builder.Default
    private List<Ticket> tickets = new ArrayList<>();
}
