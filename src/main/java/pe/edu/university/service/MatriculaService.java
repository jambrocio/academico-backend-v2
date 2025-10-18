package pe.edu.university.service;

import pe.edu.university.dto.MatriculaDto;
import java.util.List;

public interface MatriculaService {
    MatriculaDto create(MatriculaDto dto);
    MatriculaDto update(Long id, MatriculaDto dto);
    MatriculaDto findById(Long id);
    List<MatriculaDto> findAll();
    void delete(Long id);
}
