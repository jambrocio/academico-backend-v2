package pe.edu.university.service;

import pe.edu.university.dto.CursoDto;
import java.util.List;

public interface CursoService {
    CursoDto create(CursoDto dto);
    CursoDto update(Long id, CursoDto dto);
    CursoDto findById(Long id);
    List<CursoDto> findAll();
    void delete(Long id);
}
