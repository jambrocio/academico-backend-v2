package pe.edu.university.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "estudiante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estudiante {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estudianteId;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String telefono;
    private java.time.LocalDate fechaNacimiento;
    private String direccion;
    private OffsetDateTime fechaRegistro;
    private Boolean activo;
    @PrePersist public void prePersist(){ if(fechaRegistro==null) fechaRegistro=OffsetDateTime.now(); if(activo==null) activo=true; }
}
