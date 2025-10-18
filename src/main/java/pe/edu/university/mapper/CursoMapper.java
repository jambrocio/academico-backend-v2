package pe.edu.university.mapper;

import org.springframework.stereotype.Component;
import pe.edu.university.dto.CursoDto;
import pe.edu.university.entity.Curso;

@Component
public class CursoMapper {
    public CursoDto toDto(Curso e){
        if(e==null) return null;
        return CursoDto.builder()
                .cursoId(e.getCursoId())
                .carreraId(e.getCarreraId())
                .codigo(e.getCodigo())
                .nombre(e.getNombre())
                .creditos(e.getCreditos())
                .nivelSemestre(e.getNivelSemestre())
                .build();
    }
    public Curso toEntity(CursoDto d){
        if(d==null) return null;
        Curso e = new Curso();
        e.setCodigo(d.getCodigo());
        e.setNombre(d.getNombre());
        e.setCreditos(d.getCreditos());
        e.setNivelSemestre(d.getNivelSemestre());
        return e;
    }
}
