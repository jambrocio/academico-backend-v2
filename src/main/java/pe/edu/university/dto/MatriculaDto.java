package pe.edu.university.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.*;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MatriculaDto {
    private Long matriculaId;
    private LocalDate fechaMatricula;
    private String estado;
    private BigDecimal costo;
    private String metodoPago;
    private LocalDateTime fechaRegistro;

    private Long estudianteId;
    private String nombreEstudiante;
    private String correoEstudiante;

    private Long seccionId;
    private String codigoSeccion;
    private String nombreCurso;
    private String nombreProfesor;
}
