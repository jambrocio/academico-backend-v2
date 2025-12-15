package pe.edu.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.SeccionDto;
import pe.edu.university.entity.Seccion;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.SeccionMapper;
import pe.edu.university.repository.SeccionRepository;
import pe.edu.university.service.SeccionService;
import pe.edu.university.util.Constantes;

import java.util.List;

@Service
@Transactional
public class SeccionServiceImpl implements SeccionService {

    private final SeccionRepository repository;
    private final SeccionMapper mapper;

    @Autowired
    public SeccionServiceImpl(SeccionRepository repository, SeccionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public SeccionDto create(SeccionDto dto) {
        Seccion e = mapper.toEntity(dto);
        Seccion saved = repository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public SeccionDto update(Long id, SeccionDto dto) {
        Seccion existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.SECCION_NO_ENCONTRADO + id));
        // simple field updates (mapper could be more advanced)
        Seccion updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public SeccionDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.SECCION_NO_ENCONTRADO + id));
    }

    @Override
    public List<SeccionDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        Seccion e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.SECCION_NO_ENCONTRADO + id));
        repository.delete(e);
    }
}
