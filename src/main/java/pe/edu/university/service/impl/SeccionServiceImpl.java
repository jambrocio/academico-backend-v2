package pe.edu.university.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.SeccionDto;
import pe.edu.university.entity.Seccion;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.SeccionMapper;
import pe.edu.university.repository.SeccionRepository;
import pe.edu.university.service.SeccionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SeccionServiceImpl implements SeccionService {

    private final SeccionRepository repository;
    private final SeccionMapper mapper;

    @Override
    public SeccionDto create(SeccionDto dto) {
        Seccion e = mapper.toEntity(dto);
        Seccion saved = repository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public SeccionDto update(Long id, SeccionDto dto) {
        Seccion existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seccion no encontrado: " + id));
        // simple field updates (mapper could be more advanced)
        Seccion updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public SeccionDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Seccion no encontrado: " + id));
    }

    @Override
    public List<SeccionDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Seccion e = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seccion no encontrado: " + id));
        repository.delete(e);
    }
}
