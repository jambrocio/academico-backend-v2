package pe.edu.university.mapper;

import org.springframework.stereotype.Component;
import pe.edu.university.dto.SeccionDto;
import pe.edu.university.entity.Seccion;

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
        e.setCodigo(d.getCodigo());
        e.setCapacidadMaxima(d.getCapacidadMaxima());
        e.setAula(d.getAula());
        e.setHorario(d.getHorario());
        e.setDias(d.getDias());
        e.setPeriodoAcademico(d.getPeriodoAcademico());
        return e;
    }
}
