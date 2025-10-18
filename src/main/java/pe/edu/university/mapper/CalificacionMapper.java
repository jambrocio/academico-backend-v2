package pe.edu.university.mapper;

import org.springframework.stereotype.Component;
import pe.edu.university.dto.CalificacionDto;
import pe.edu.university.entity.Calificacion;

@Component
public class CalificacionMapper {
    public CalificacionDto toDto(Calificacion e){
        if(e==null) return null;
        return CalificacionDto.builder()
                .calificacionId(e.getCalificacionId())
                .matriculaId(e.getMatriculaId())
                .nota(e.getNota())
                .observacion(e.getObservacion())
                .build();
    }
    public Calificacion toEntity(CalificacionDto d){
        if(d==null) return null;
        Calificacion e = new Calificacion();
        e.setNota(d.getNota());
        e.setObservacion(d.getObservacion());
        return e;
    }
}
