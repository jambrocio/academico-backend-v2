package pe.edu.university.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "facultad")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facultad {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facultadId;
    private String nombre;
    @Column(columnDefinition = "text")
    private String descripcion;
    private String ubicacion;
    private String decano;
    private OffsetDateTime fechaRegistro;
    private Boolean activo;
    @PrePersist public void prePersist(){ if(fechaRegistro==null) fechaRegistro=OffsetDateTime.now(); if(activo==null) activo=true; }
}
