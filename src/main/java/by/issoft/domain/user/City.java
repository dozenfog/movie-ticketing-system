package by.issoft.domain.user;

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
@Table(name = "city")
@SuperBuilder(toBuilder = true)
public class City extends AbstractEntity {
    @Column(nullable = false, unique = true, length = 50)
    private String name;
}
