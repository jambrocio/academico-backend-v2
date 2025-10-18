package pe.edu.university.service;

import pe.edu.university.dto.SeccionDto;
import java.util.List;

public interface SeccionService {
    SeccionDto create(SeccionDto dto);
    SeccionDto update(Long id, SeccionDto dto);
    SeccionDto findById(Long id);
    List<SeccionDto> findAll();
    void delete(Long id);
}
