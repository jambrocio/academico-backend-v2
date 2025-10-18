package pe.edu.university.mapper;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import pe.edu.university.dto.CarreraDto;
import pe.edu.university.entity.Carrera;
import java.lang.reflect.Method;

@Component
public class CarreraMapper {

    public Carrera toEntity(CarreraDto dto) {
        if (dto == null) return null;
        Carrera entity = new Carrera();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public CarreraDto toDto(Carrera entity) {
        if (entity == null) return null;
        CarreraDto dto = new CarreraDto();
        BeanUtils.copyProperties(entity, dto);
        // mapear facultadId si existe la relación (usar reflexión para evitar supuestos sobre el nombre del getter)
        if (entity.getFacultad() != null) {
            Long fid = extractFacultadId(entity.getFacultad());
            if (fid != null) {
                dto.setFacultadId(fid);
            }
        }
        return dto;
    }

    public void updateEntity(CarreraDto dto, Carrera entity) {
        if (dto == null || entity == null) return;
        BeanUtils.copyProperties(dto, entity, "id");
    }

    // Helper: intenta obtener un identificador de la entidad Facultad probando getters comunes
    private Long extractFacultadId(Object facultad) {
        if (facultad == null) return null;
        String[] candidateGetters = { "getId", "getFacultadId", "getIdFacultad" };
        for (String getter : candidateGetters) {
            try {
                Method m = facultad.getClass().getMethod(getter);
                Object val = m.invoke(facultad);
                if (val == null) return null;
                if (val instanceof Number) {
                    return ((Number) val).longValue();
                }
                if (val instanceof String) {
                    try {
                        return Long.valueOf((String) val);
                    } catch (NumberFormatException ignored) { }
                }
            } catch (Exception ignored) {
                // método no encontrado o invocación falló; probar siguiente candidato
            }
        }
        return null;
    }
}
