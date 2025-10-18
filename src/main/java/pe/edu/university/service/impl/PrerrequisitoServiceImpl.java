package pe.edu.university.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.PrerrequisitoDto;
import pe.edu.university.entity.Prerrequisito;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.PrerrequisitoMapper;
import pe.edu.university.repository.PrerrequisitoRepository;
import pe.edu.university.service.PrerrequisitoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PrerrequisitoServiceImpl implements PrerrequisitoService {

    @Autowired
    PrerrequisitoRepository repository;

    @Autowired
    PrerrequisitoMapper mapper;

    @Override
    public PrerrequisitoDto create(PrerrequisitoDto dto) {
        Prerrequisito e = mapper.toEntity(dto);
        Prerrequisito saved = repository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public PrerrequisitoDto update(Long id, PrerrequisitoDto dto) {
        Prerrequisito existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prerrequisito no encontrado: " + id));
        // simple field updates (mapper could be more advanced)
        Prerrequisito updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public PrerrequisitoDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Prerrequisito no encontrado: " + id));
    }

    @Override
    public List<PrerrequisitoDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Prerrequisito e = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prerrequisito no encontrado: " + id));
        repository.delete(e);
    }

    // Corregir tipos y converters que apuntaban a Seccion
    public List<PrerrequisitoDto> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public PrerrequisitoDto listarId(Long id) {
        Prerrequisito p = repository.findById(id).orElse(null);
        return mapper.toDto(p);
    }

    public PrerrequisitoDto guardar(PrerrequisitoDto dto) {
        Prerrequisito e = mapper.toEntity(dto);
        e = repository.save(e);
        return mapper.toDto(e);
    }
}
