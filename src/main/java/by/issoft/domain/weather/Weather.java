package by.issoft.domain.weather;

import by.issoft.domain.AbstractEntity;
import by.issoft.domain.user.City;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "weather")
@SuperBuilder(toBuilder = true)
public class Weather extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "date_time", nullable = false)
    @CreationTimestamp
    private LocalDateTime datetime;

    @Column(nullable = false)
    private Double temperature;

    @Column(name = "feels_like", nullable = false)
    private Double feelsLike;

    @Column(nullable = false)
    private Double pressure;

    @Column(nullable = false)
    private Double humidity;

    @Column(name = "min_temp", nullable = false)
    private Double minTemperature;

    @Column(name = "max_temp", nullable = false)
    private Double maxTemperature;

    @Column(nullable = false)
    private Integer visibility;

    @Column(name = "wind_speed", nullable = false)
    private Double windSpeed;

    @Column(name = "wind_degree", nullable = false)
    private Double windDegree;

    @Column(nullable = false)
    private Double cloudiness;
}
