package pe.edu.university.service;

import pe.edu.university.dto.CarreraDto;
import java.util.List;

public interface CarreraService {
    CarreraDto create(CarreraDto dto);
    CarreraDto update(Long id, CarreraDto dto);
    CarreraDto findById(Long id);
    List<CarreraDto> findAll();
    void delete(Long id);
}
