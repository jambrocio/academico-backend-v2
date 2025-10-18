package pe.edu.university.mapper;

import org.springframework.stereotype.Component;
import pe.edu.university.dto.ProfesorDto;
import pe.edu.university.entity.Profesor;

@Component
public class ProfesorMapper {
    public ProfesorDto toDto(Profesor e){
        if(e==null) return null;
        return ProfesorDto.builder()
                .profesorId(e.getProfesorId())
                .nombre(e.getNombre())
                .apellido(e.getApellido())
                .dni(e.getDni())
                .email(e.getEmail())
                .build();
    }
    public Profesor toEntity(ProfesorDto d){
        if(d==null) return null;
        Profesor e = new Profesor();
        e.setNombre(d.getNombre());
        e.setApellido(d.getApellido());
        e.setDni(d.getDni());
        e.setEmail(d.getEmail());
        return e;
    }
}
