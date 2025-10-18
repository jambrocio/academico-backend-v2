package pe.edu.university.dto;

import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SeccionDto {
    private Long seccionId;
    private Long cursoId;
    private Long profesorId;
    private String codigo;
    private Integer capacidadMaxima;
    private String aula;
    private String horario;
    private String dias;
    private String periodoAcademico;
}
