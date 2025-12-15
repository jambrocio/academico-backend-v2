package pe.edu.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.CalificacionDto;
import pe.edu.university.entity.Calificacion;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.CalificacionMapper;
import pe.edu.university.repository.CalificacionRepository;
import pe.edu.university.service.CalificacionService;
import pe.edu.university.util.Constantes;

import java.util.List;

@Service
@Transactional
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository repository;
    private final CalificacionMapper mapper;

    @Autowired
    public CalificacionServiceImpl(CalificacionRepository repository, CalificacionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CalificacionDto create(CalificacionDto dto) {
        Calificacion e = mapper.toEntity(dto);
        Calificacion saved = repository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public CalificacionDto update(Long id, CalificacionDto dto) {
        Calificacion existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.CALIFICACION_NO_ENCONTRADO + id));
        // simple field updates (mapper could be more advanced)
        Calificacion updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public CalificacionDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.CALIFICACION_NO_ENCONTRADO + id));
    }

    @Override
    public List<CalificacionDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        Calificacion e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.CALIFICACION_NO_ENCONTRADO + id));
        repository.delete(e);
    }
}
