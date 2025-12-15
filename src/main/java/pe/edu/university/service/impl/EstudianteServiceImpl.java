package pe.edu.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.EstudianteDto;
import pe.edu.university.entity.Estudiante;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.EstudianteMapper;
import pe.edu.university.repository.EstudianteRepository;
import pe.edu.university.service.EstudianteService;
import pe.edu.university.util.Constantes;

import java.util.List;

@Service
@Transactional
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository repository;
    private final EstudianteMapper mapper;

    @Autowired
    public EstudianteServiceImpl(EstudianteRepository repository, EstudianteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public EstudianteDto create(EstudianteDto dto) {
        Estudiante e = mapper.toEntity(dto);
        Estudiante saved = repository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public EstudianteDto update(Long id, EstudianteDto dto) {
        Estudiante existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.ESTUDIANTE_NO_ENCONTRADO + id));
        if (dto.getNombre() != null)
            existing.setNombre(dto.getNombre());
        if (dto.getApellido() != null)
            existing.setApellido(dto.getApellido());
        if (dto.getDni() != null)
            existing.setDni(dto.getDni());
        if (dto.getEmail() != null)
            existing.setEmail(dto.getEmail());

        Estudiante updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public EstudianteDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.ESTUDIANTE_NO_ENCONTRADO + id));
    }

    @Override
    public List<EstudianteDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        Estudiante e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.ESTUDIANTE_NO_ENCONTRADO + id));
        repository.delete(e);
    }
}
