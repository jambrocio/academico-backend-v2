package pe.edu.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.PrerrequisitoDto;
import pe.edu.university.entity.Prerrequisito;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.PrerrequisitoMapper;
import pe.edu.university.repository.PrerrequisitoRepository;
import pe.edu.university.service.PrerrequisitoService;
import pe.edu.university.util.Constantes;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PrerrequisitoServiceImpl implements PrerrequisitoService {

    private final PrerrequisitoRepository repository;
    private final PrerrequisitoMapper mapper;

    @Autowired
    public PrerrequisitoServiceImpl(PrerrequisitoRepository repository, PrerrequisitoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PrerrequisitoDto create(PrerrequisitoDto dto) {
        Prerrequisito e = mapper.toEntity(dto);
        Prerrequisito saved = repository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public PrerrequisitoDto update(Long id, PrerrequisitoDto dto) {
        Prerrequisito existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.PRE_REQUISITO_NO_ENCONTRADO + id));
        // simple field updates (mapper could be more advanced)
        Prerrequisito updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public PrerrequisitoDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.PRE_REQUISITO_NO_ENCONTRADO + id));
    }

    @Override
    public List<PrerrequisitoDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        Prerrequisito e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.PRE_REQUISITO_NO_ENCONTRADO + id));
        repository.delete(e);
    }

    // Corregir tipos y converters que apuntaban a Seccion
    public List<PrerrequisitoDto> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
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
