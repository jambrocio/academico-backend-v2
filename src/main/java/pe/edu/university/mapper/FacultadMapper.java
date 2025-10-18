package pe.edu.university.mapper;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import pe.edu.university.dto.FacultadDto;
import pe.edu.university.entity.Facultad;

@Component
public class FacultadMapper {

    public Facultad toEntity(FacultadDto dto) {
        if (dto == null) return null;
        Facultad entity = new Facultad();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public FacultadDto toDto(Facultad entity) {
        if (entity == null) return null;
        FacultadDto dto = new FacultadDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public void updateEntity(FacultadDto dto, Facultad entity) {
        if (dto == null || entity == null) return;
        BeanUtils.copyProperties(dto, entity, "id");
    }
}
