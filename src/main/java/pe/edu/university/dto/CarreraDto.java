package pe.edu.university.dto;

import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CarreraDto {
    private Long carreraId;
    private Long facultadId;
    private String nombre;
    private Integer duracionSemestres;
}
