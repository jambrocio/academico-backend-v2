package pe.edu.university.service;

import pe.edu.university.dto.FacultadDto;
import java.util.List;

public interface FacultadService {
    FacultadDto create(FacultadDto dto);
    FacultadDto update(Long id, FacultadDto dto);
    FacultadDto findById(Long id);
    List<FacultadDto> findAll();
    void delete(Long id);
}
