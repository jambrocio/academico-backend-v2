package pe.edu.university.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.MatriculaDto;
import pe.edu.university.entity.Matricula;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.MatriculaMapper;
import pe.edu.university.repository.MatriculaRepository;
import pe.edu.university.service.MatriculaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MatriculaServiceImpl implements MatriculaService {

    @Autowired
    MatriculaRepository repository;

    @Autowired
    MatriculaMapper mapper;

    @Override
    public MatriculaDto create(MatriculaDto dto) {
        Matricula e = mapper.toEntity(dto);
        Matricula saved = repository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public MatriculaDto update(Long id, MatriculaDto dto) {
        Matricula existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Matricula no encontrado: " + id));
        // simple field updates (mapper could be more advanced)
        Matricula updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public MatriculaDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Matricula no encontrado: " + id));
    }

    @Override
    public List<MatriculaDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Matricula e = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Matricula no encontrado: " + id));
        repository.delete(e);
    }
}
