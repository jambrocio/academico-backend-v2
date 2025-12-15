package pe.edu.university.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.*;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "matricula")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matriculaId;

    @Column(name = "estudiante_id", insertable = false, updatable = false)
    private Long estudianteId;

    @Column(name = "seccion_id", insertable = false, updatable = false)
    private Long seccionId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaMatricula;

    private String estado;
    private BigDecimal costo;
    private String metodoPago;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        if (fechaMatricula == null)
            fechaMatricula = LocalDate.now();
        if (fechaRegistro == null)
            fechaRegistro = LocalDateTime.now();
        if (estado == null)
            estado = "PENDIENTE";
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seccion_id", nullable = false)
    private Seccion seccion;

    @OneToOne(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private Calificacion calificacion;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Pago> pagos;
}
