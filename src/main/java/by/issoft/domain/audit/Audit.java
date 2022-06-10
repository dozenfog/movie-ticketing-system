package by.issoft.domain.audit;

import by.issoft.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "audit")
@SuperBuilder(toBuilder = true)
public class Audit extends AbstractEntity {
    @Column(name = "class_name", nullable = false)
    private String className;

    @Column(name = "method_name", nullable = false)
    private String methodName;

    @Column(nullable = false)
    private String args;

    @Column(name = "return_value")
    @Lob
    private String returnValue;

    @Column
    private String exception;

    @Column(name = "execution_time_nano", nullable = false)
    private long executionTimeInNano;

    @Column(name = "invocation_time", nullable = false)
    private LocalDateTime invocationTime;
}
