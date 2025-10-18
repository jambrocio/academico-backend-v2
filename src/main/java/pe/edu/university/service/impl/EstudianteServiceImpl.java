package pe.edu.university.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.EstudianteDto;
import pe.edu.university.entity.Estudiante;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.EstudianteMapper;
import pe.edu.university.repository.EstudianteRepository;
import pe.edu.university.service.EstudianteService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository repository;
    private final EstudianteMapper mapper;

    @Override
    public EstudianteDto create(EstudianteDto dto) {
        Estudiante e = mapper.toEntity(dto);
        Estudiante saved = repository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public EstudianteDto update(Long id, EstudianteDto dto) {
        Estudiante existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado: " + id));
        // simple field updates (mapper could be more advanced)
        Estudiante updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public EstudianteDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado: " + id));
    }

    @Override
    public List<EstudianteDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Estudiante e = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado: " + id));
        repository.delete(e);
    }
}
