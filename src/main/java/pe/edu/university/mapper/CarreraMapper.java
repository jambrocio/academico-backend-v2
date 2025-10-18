package pe.edu.university.mapper;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import pe.edu.university.dto.CarreraDto;
import pe.edu.university.entity.Carrera;

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
        return dto;
    }

    public void updateEntity(CarreraDto dto, Carrera entity) {
        if (dto == null || entity == null) return;
        BeanUtils.copyProperties(dto, entity, "id");
    }
}
