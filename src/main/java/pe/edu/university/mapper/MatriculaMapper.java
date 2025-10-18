package pe.edu.university.mapper;

import org.springframework.stereotype.Component;
import pe.edu.university.dto.MatriculaDto;
import pe.edu.university.entity.Matricula;

@Component
public class MatriculaMapper {
    public MatriculaDto toDto(Matricula e){
        if(e==null) return null;
        MatriculaDto d = MatriculaDto.builder()
                .matriculaId(e.getMatriculaId())
                .fechaMatricula(e.getFechaMatricula())
                .estado(e.getEstado())
                .costo(e.getCosto())
                .metodoPago(e.getMetodoPago())
                .fechaRegistro(e.getFechaRegistro())
                .build();
        if(e.getEstudiante()!=null){
            d.setEstudianteId(e.getEstudiante().getEstudianteId());
            d.setNombreEstudiante(e.getEstudiante().getNombre()+" "+e.getEstudiante().getApellido());
        }
        if(e.getSeccion()!=null){
            d.setSeccionId(e.getSeccion().getSeccionId());
            d.setCodigoSeccion(e.getSeccion().getCodigo());
            if(e.getSeccion().getCurso()!=null) d.setNombreCurso(e.getSeccion().getCurso().getNombre());
            if(e.getSeccion().getProfesor()!=null) d.setNombreProfesor(e.getSeccion().getProfesor().getNombre()+" "+e.getSeccion().getProfesor().getApellido());
        }
        return d;
    }
    public Matricula toEntity(MatriculaDto d){
        if(d==null) return null;
        Matricula e = new Matricula();
        e.setFechaMatricula(d.getFechaMatricula());
        e.setEstado(d.getEstado());
        e.setCosto(d.getCosto());
        e.setMetodoPago(d.getMetodoPago());
        return e;
    }
}
