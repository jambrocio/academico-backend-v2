package pe.edu.university.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "prerrequisito")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prerrequisito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prerrequisitoId;

    @Column(name = "curso_id", insertable = false, updatable = false)
    private Long cursoId;

    @Column(name = "curso_req_id", insertable = false, updatable = false)
    private Long cursoReqId;

    private OffsetDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        if (fechaRegistro == null)
            fechaRegistro = OffsetDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_req_id", nullable = false)
    private Curso cursoRequerido;
}
