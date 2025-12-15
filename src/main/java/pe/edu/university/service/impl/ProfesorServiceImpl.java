package pe.edu.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.ProfesorDto;
import pe.edu.university.entity.Profesor;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.ProfesorMapper;
import pe.edu.university.repository.ProfesorRepository;
import pe.edu.university.service.ProfesorService;
import pe.edu.university.util.Constantes;

import java.util.List;

@Service
@Transactional
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorRepository repository;
    private final ProfesorMapper mapper;

    @Autowired
    public ProfesorServiceImpl(ProfesorRepository repository, ProfesorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ProfesorDto create(ProfesorDto dto) {
        Profesor e = mapper.toEntity(dto);
        Profesor saved = repository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public ProfesorDto update(Long id, ProfesorDto dto) {
        Profesor existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.PROFESOR_NO_ENCONTRADO + id));
        if (dto.getNombre() != null)
            existing.setNombre(dto.getNombre());
        if (dto.getApellido() != null)
            existing.setApellido(dto.getApellido());
        if (dto.getDni() != null)
            existing.setDni(dto.getDni());
        if (dto.getEmail() != null)
            existing.setEmail(dto.getEmail());

        Profesor updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public ProfesorDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.PROFESOR_NO_ENCONTRADO + id));
    }

    @Override
    public List<ProfesorDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        Profesor e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.PROFESOR_NO_ENCONTRADO + id));
        repository.delete(e);
    }
}
