package pe.edu.university.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.*;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CalificacionDto {
    private Long calificacionId;
    private Long matriculaId;
    private BigDecimal nota;
    private String observacion;
}
