package pe.edu.university.mapper;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import pe.edu.university.dto.CursoDto;
import pe.edu.university.entity.Curso;
import java.lang.reflect.Method;

@Component
public class CursoMapper {

    public Curso toEntity(CursoDto dto) {
        if (dto == null) return null;
        Curso entity = new Curso();
        BeanUtils.copyProperties(dto, entity);
        // NOTA: no asignar Carrera aquí; lo hace el service para resolver desde BD
        return entity;
    }

    public CursoDto toDto(Curso entity) {
        if (entity == null) return null;
        CursoDto dto = new CursoDto();
        BeanUtils.copyProperties(entity, dto);
        if (entity.getCarrera() != null) {
            Long cid = extractCarreraId(entity.getCarrera());
            if (cid != null) dto.setCarreraId(cid);
        }
        return dto;
    }

    public void updateEntity(CursoDto dto, Curso entity) {
        if (dto == null || entity == null) return;
        BeanUtils.copyProperties(dto, entity, "id");
        // No modificar la relación Carrera aquí; su manejo puede depender del service
    }

    private Long extractCarreraId(Object carrera) {
        if (carrera == null) return null;
        String[] candidateGetters = { "getId", "getCarreraId", "getIdCarrera" };
        for (String getter : candidateGetters) {
            try {
                Method m = carrera.getClass().getMethod(getter);
                Object val = m.invoke(carrera);
                if (val == null) return null;
                if (val instanceof Number) return ((Number) val).longValue();
                if (val instanceof String) {
                    try { return Long.valueOf((String) val); } catch (NumberFormatException ignored) {}
                }
            } catch (Exception ignored) {
                // probar siguiente candidato
            }
        }
        return null;
    }
}
