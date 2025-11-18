package pe.edu.university.mapper;

import org.springframework.stereotype.Component;
import pe.edu.university.dto.SeccionDto;
import pe.edu.university.entity.Seccion;
import pe.edu.university.entity.Curso;
import pe.edu.university.entity.Profesor;

@Component
public class SeccionMapper {
    public SeccionDto toDto(Seccion e){
        if(e==null) return null;
        return SeccionDto.builder()
                .seccionId(e.getSeccionId())
                .cursoId(e.getCursoId())
                .profesorId(e.getProfesorId())
                .codigo(e.getCodigo())
                .capacidadMaxima(e.getCapacidadMaxima())
                .aula(e.getAula())
                .horario(e.getHorario())
                .dias(e.getDias())
                .periodoAcademico(e.getPeriodoAcademico())
                .build();
    }
    public Seccion toEntity(SeccionDto d){
        if(d==null) return null;
        Seccion e = new Seccion();
        e.setSeccionId(d.getSeccionId());
        e.setCursoId(d.getCursoId());
        e.setProfesorId(d.getProfesorId());
        // set relation objects so JPA will persist FK columns (curso/profesor)
        if(d.getCursoId()!=null){
            Curso c = new Curso();
            c.setCursoId(d.getCursoId());
            e.setCurso(c);
        }
        if(d.getProfesorId()!=null){
            Profesor p = new Profesor();
            p.setProfesorId(d.getProfesorId());
            e.setProfesor(p);
        }
        e.setCodigo(d.getCodigo());
        e.setCapacidadMaxima(d.getCapacidadMaxima());
        e.setAula(d.getAula());
        e.setHorario(d.getHorario());
        e.setDias(d.getDias());
        e.setPeriodoAcademico(d.getPeriodoAcademico());
        return e;
    }
}
