package pe.edu.university.service;

import pe.edu.university.dto.PagoDto;
import java.util.List;

public interface PagoService {
    PagoDto create(PagoDto dto);
    PagoDto update(Long id, PagoDto dto);
    PagoDto findById(Long id);
    List<PagoDto> findAll();
    void delete(Long id);
}
