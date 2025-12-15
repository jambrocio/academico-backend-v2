package pe.edu.university.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "profesor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profesorId;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String telefono;
    private String especialidad;
    private String tituloAcademico;
    private OffsetDateTime fechaRegistro;
    private Boolean activo;

    @PrePersist
    public void prePersist() {
        if (fechaRegistro == null)
            fechaRegistro = OffsetDateTime.now();
        if (activo == null)
            activo = true;
    }
}
