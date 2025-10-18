package pe.edu.university.service;

import pe.edu.university.dto.EstudianteDto;
import java.util.List;

public interface EstudianteService {
    EstudianteDto create(EstudianteDto dto);
    EstudianteDto update(Long id, EstudianteDto dto);
    EstudianteDto findById(Long id);
    List<EstudianteDto> findAll();
    void delete(Long id);
}
