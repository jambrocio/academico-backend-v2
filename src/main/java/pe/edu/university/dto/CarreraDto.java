package pe.edu.university.dto;

import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CarreraDto {
    private Long carreraId;
    private Long facultadId;
    private String nombre;
    private Integer duracionSemestres;

    // Nuevo campo para recibir/mostrar la relación con Facultad
    public Long getFacultadId() {
        return facultadId;
    }

    public void setFacultadId(Long facultadId) {
        this.facultadId = facultadId;
    }
}
