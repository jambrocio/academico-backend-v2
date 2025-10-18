package pe.edu.university.mapper;

import org.springframework.stereotype.Component;
import pe.edu.university.dto.EstudianteDto;
import pe.edu.university.entity.Estudiante;

@Component
public class EstudianteMapper {
    public EstudianteDto toDto(Estudiante e){
        if(e==null) return null;
        return EstudianteDto.builder()
                .estudianteId(e.getEstudianteId())
                .nombre(e.getNombre())
                .apellido(e.getApellido())
                .dni(e.getDni())
                .email(e.getEmail())
                .build();
    }
    public Estudiante toEntity(EstudianteDto d){
        if(d==null) return null;
        Estudiante e = new Estudiante();
        e.setNombre(d.getNombre());
        e.setApellido(d.getApellido());
        e.setDni(d.getDni());
        e.setEmail(d.getEmail());
        return e;
    }
}
