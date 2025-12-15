package pe.edu.university.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pagoId;

    @Column(name = "matricula_id", insertable = false, updatable = false)
    private Long matriculaId;

    private java.time.LocalDate fechaPago;
    private BigDecimal monto;
    private String metodoPago;
    private String referencia;
    private String estado;
    private OffsetDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        if (fechaPago == null)
            fechaPago = java.time.LocalDate.now();
        if (fechaRegistro == null)
            fechaRegistro = OffsetDateTime.now();
        if (estado == null)
            estado = "PENDIENTE";
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_id", nullable = false)
    private Matricula matricula;
}
