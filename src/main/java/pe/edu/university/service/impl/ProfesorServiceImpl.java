package pe.edu.university.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.ProfesorDto;
import pe.edu.university.entity.Profesor;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.ProfesorMapper;
import pe.edu.university.repository.ProfesorRepository;
import pe.edu.university.service.ProfesorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfesorServiceImpl implements ProfesorService {

    @Autowired
    ProfesorRepository repository;

    @Autowired
    ProfesorMapper mapper;

    @Override
    public ProfesorDto create(ProfesorDto dto) {
        Profesor e = mapper.toEntity(dto);
        Profesor saved = repository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public ProfesorDto update(Long id, ProfesorDto dto) {
        Profesor existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado: " + id));
        // simple field updates (mapper could be more advanced)
        Profesor updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public ProfesorDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado: " + id));
    }

    @Override
    public List<ProfesorDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Profesor e = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado: " + id));
        repository.delete(e);
    }
}
