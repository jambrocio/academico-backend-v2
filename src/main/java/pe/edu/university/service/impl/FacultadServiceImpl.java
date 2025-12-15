package pe.edu.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.FacultadDto;
import pe.edu.university.entity.Facultad;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.FacultadMapper;
import pe.edu.university.repository.FacultadRepository;
import pe.edu.university.service.FacultadService;
import pe.edu.university.util.Constantes;

import java.util.List;

@Service
@Transactional
public class FacultadServiceImpl implements FacultadService {

    private final FacultadRepository facultadRepository;
    private final FacultadMapper mapper;

    @Autowired
    public FacultadServiceImpl(FacultadRepository facultadRepository, FacultadMapper mapper) {
        this.facultadRepository = facultadRepository;
        this.mapper = mapper;
    }

    @Override
    public FacultadDto create(FacultadDto dto) {
        Facultad e = mapper.toEntity(dto);
        Facultad saved = facultadRepository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public FacultadDto update(Long id, FacultadDto dto) {
        Facultad existing = facultadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.FACULTAD_NO_ENCONTRADO + id));
        // simple field updates (mapper could be more advanced)
        Facultad updated = facultadRepository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public FacultadDto findById(Long id) {
        return facultadRepository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.FACULTAD_NO_ENCONTRADO + id));
    }

    @Override
    public List<FacultadDto> findAll() {
        return facultadRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        Facultad e = facultadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.FACULTAD_NO_ENCONTRADO + id));
        facultadRepository.delete(e);
    }
}
