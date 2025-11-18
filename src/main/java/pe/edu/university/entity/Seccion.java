package pe.edu.university.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "seccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seccion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seccionId;

    @Column(name = "curso_id", insertable = false, updatable = false)
    private Long cursoId;

    @Column(name = "profesor_id", insertable = false, updatable = false)
    private Long profesorId;

    private String codigo;
    private Integer capacidadMaxima;
    private String aula;
    private String horario;
    private String dias;
    private String periodoAcademico;
    private java.time.LocalDate fechaInicio;
    private java.time.LocalDate fechaFin;
    private OffsetDateTime fechaRegistro;
    private Boolean activo;
    @PrePersist public void prePersist(){ if(fechaRegistro==null) fechaRegistro=OffsetDateTime.now(); if(activo==null) activo=true; }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id", nullable = false)
    private Profesor profesor;

    @OneToMany(mappedBy = "seccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Matricula> matriculas;
}
