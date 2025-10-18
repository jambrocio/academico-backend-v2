package pe.edu.university.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.*;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PagoDto {
    private Long pagoId;
    private Long matriculaId;
    private LocalDate fechaPago;
    private BigDecimal monto;
    private String metodoPago;
    private String referencia;
    private String estado;
}
