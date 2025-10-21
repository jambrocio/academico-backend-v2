package pe.edu.university.dto;

import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CursoDto {
    private Long cursoId;
    private Long carreraId;
    private String codigo;
    private String nombre;
    private Integer creditos;
    private Integer nivelSemestre;
}
