package pe.edu.university.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "calificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calificacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calificacionId;

    @Column(name = "matricula_id", insertable = false, updatable = false)
    private Long matriculaId;

    private BigDecimal nota;
    private String observacion;
    private OffsetDateTime fechaRegistro;

    @PrePersist public void prePersist(){ if(fechaRegistro==null) fechaRegistro=OffsetDateTime.now(); }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_id", nullable = false, unique = true)
    private Matricula matricula;
}
