package pe.edu.university.mapper;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import pe.edu.university.dto.PrerrequisitoDto;
import pe.edu.university.entity.Prerrequisito;

@Component
public class PrerrequisitoMapper {

    public Prerrequisito toEntity(PrerrequisitoDto dto) {
        if (dto == null) return null;
        Prerrequisito entity = new Prerrequisito();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public PrerrequisitoDto toDto(Prerrequisito entity) {
        if (entity == null) return null;
        PrerrequisitoDto dto = new PrerrequisitoDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public void updateEntity(PrerrequisitoDto dto, Prerrequisito entity) {
        if (dto == null || entity == null) return;
        BeanUtils.copyProperties(dto, entity, "id");
    }
}
