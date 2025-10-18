package pe.edu.university.service;

import pe.edu.university.dto.CalificacionDto;
import java.util.List;

public interface CalificacionService {
    CalificacionDto create(CalificacionDto dto);
    CalificacionDto update(Long id, CalificacionDto dto);
    CalificacionDto findById(Long id);
    List<CalificacionDto> findAll();
    void delete(Long id);
}
