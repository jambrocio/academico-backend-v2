package pe.edu.university.dto;

import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EstudianteDto {
    private Long estudianteId;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
}
