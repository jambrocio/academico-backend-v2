package pe.edu.university.dto;

import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class FacultadDto {
    private Long facultadId;
    private String nombre;
    private String descripcion;
}
