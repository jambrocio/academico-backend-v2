package pe.edu.university.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.CarreraDto;
import pe.edu.university.entity.Carrera;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.CarreraMapper;
import pe.edu.university.repository.CarreraRepository;
import pe.edu.university.service.CarreraService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CarreraServiceImpl implements CarreraService {

    private final CarreraRepository repository;
    private final CarreraMapper mapper;

    @Override
    public CarreraDto create(CarreraDto dto) {
        Carrera e = mapper.toEntity(dto);
        Carrera saved = repository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public CarreraDto update(Long id, CarreraDto dto) {
        Carrera existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrado: " + id));
        // simple field updates (mapper could be more advanced)
        Carrera updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public CarreraDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrado: " + id));
    }

    @Override
    public List<CarreraDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Carrera e = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Carrera no encontrado: " + id));
        repository.delete(e);
    }
}
