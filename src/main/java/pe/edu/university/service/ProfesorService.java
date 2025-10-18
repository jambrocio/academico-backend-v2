package pe.edu.university.service;

import pe.edu.university.dto.ProfesorDto;
import java.util.List;

public interface ProfesorService {
    ProfesorDto create(ProfesorDto dto);
    ProfesorDto update(Long id, ProfesorDto dto);
    ProfesorDto findById(Long id);
    List<ProfesorDto> findAll();
    void delete(Long id);
}
