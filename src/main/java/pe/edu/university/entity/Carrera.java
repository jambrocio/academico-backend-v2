package pe.edu.university.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.util.List;

@Entity
@Table(name = "carrera")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrera {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carreraId;

    @Column(name = "facultad_id", insertable = false, updatable = false)
    private Long facultadId;

    private String nombre;
    @Column(columnDefinition = "text")
    private String descripcion;
    private Integer duracionSemestres;
    private String tituloOtorgado;
    private OffsetDateTime fechaRegistro;
    private Boolean activo;

    @PrePersist public void prePersist(){ if(fechaRegistro==null) fechaRegistro=OffsetDateTime.now(); if(activo==null) activo=true; }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facultad_id", nullable = false)
    private Facultad facultad;

    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Curso> cursos;
}
