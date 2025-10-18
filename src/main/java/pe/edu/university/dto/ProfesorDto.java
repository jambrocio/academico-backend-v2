package pe.edu.university.dto;

import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProfesorDto {
    private Long profesorId;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
}
