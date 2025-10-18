package pe.edu.university.service;

import pe.edu.university.dto.PrerrequisitoDto;
import java.util.List;

public interface PrerrequisitoService {
    PrerrequisitoDto create(PrerrequisitoDto dto);
    PrerrequisitoDto update(Long id, PrerrequisitoDto dto);
    PrerrequisitoDto findById(Long id);
    List<PrerrequisitoDto> findAll();
    void delete(Long id);
}
