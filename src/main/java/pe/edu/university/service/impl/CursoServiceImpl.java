package pe.edu.university.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.CursoDto;
import pe.edu.university.entity.Curso;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.CursoMapper;
import pe.edu.university.repository.CursoRepository;
import pe.edu.university.service.CursoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CursoServiceImpl implements CursoService {

    private final CursoRepository repository;
    private final CursoMapper mapper;

    @Override
    public CursoDto create(CursoDto dto) {
        Curso e = mapper.toEntity(dto);
        Curso saved = repository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public CursoDto update(Long id, CursoDto dto) {
        Curso existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado: " + id));
        // simple field updates (mapper could be more advanced)
        Curso updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public CursoDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado: " + id));
    }

    @Override
    public List<CursoDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Curso e = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado: " + id));
        repository.delete(e);
    }
}
