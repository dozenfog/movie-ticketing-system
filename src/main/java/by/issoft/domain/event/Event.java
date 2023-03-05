package by.issoft.domain.event;

import by.issoft.domain.AbstractEntity;
import by.issoft.domain.cinema.MovieRoom;
import by.issoft.domain.order.Order;
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
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(exclude = {"orders", "movieRoom"})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event")
@SuperBuilder(toBuilder = true)
public class Event extends AbstractEntity {
    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Enumerated
    @Column(name = "event_status", nullable = false)
    private EventStatus eventStatus;

    @ManyToOne
    @JoinColumn(name = "movie_room_id")
    private MovieRoom movieRoom;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @Builder.Default
    private List<Order> orders = new ArrayList<>();
}
