package pe.edu.university.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.CalificacionDto;
import pe.edu.university.entity.Calificacion;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.CalificacionMapper;
import pe.edu.university.repository.CalificacionRepository;
import pe.edu.university.service.CalificacionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository repository;
    private final CalificacionMapper mapper;

    @Override
    public CalificacionDto create(CalificacionDto dto) {
        Calificacion e = mapper.toEntity(dto);
        Calificacion saved = repository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public CalificacionDto update(Long id, CalificacionDto dto) {
        Calificacion existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Calificacion no encontrado: " + id));
        // simple field updates (mapper could be more advanced)
        Calificacion updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public CalificacionDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Calificacion no encontrado: " + id));
    }

    @Override
    public List<CalificacionDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Calificacion e = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Calificacion no encontrado: " + id));
        repository.delete(e);
    }
}
