package by.issoft.domain.cinema;

import by.issoft.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "room_type")
@SuperBuilder(toBuilder = true)
public class RoomType extends AbstractEntity {
    @Column(unique = true, nullable = false, length = 10)
    private String name;

    @Column(name = "seat_price", nullable = false)
    private BigDecimal seatPrice;
}
