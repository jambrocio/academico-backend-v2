package pe.edu.university.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.util.List;

@Entity
@Table(name = "curso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curso {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cursoId;

    @Column(name = "carrera_id", insertable = false, updatable = false)
    private Long carreraId;

    private String codigo;
    private String nombre;
    @Column(columnDefinition = "text")
    private String descripcion;
    private Integer creditos;
    private Integer nivelSemestre;
    private OffsetDateTime fechaRegistro;
    private Boolean activo;

    @PrePersist public void prePersist(){ if(fechaRegistro==null) fechaRegistro=OffsetDateTime.now(); if(activo==null) activo=true; }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrera_id", nullable = false)
    private Carrera carrera;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seccion> secciones;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prerrequisito> prerrequisitos;
}
