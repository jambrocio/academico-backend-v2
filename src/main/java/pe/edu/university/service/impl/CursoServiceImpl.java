package pe.edu.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.CursoDto;
import pe.edu.university.entity.Curso;
import pe.edu.university.entity.Carrera;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.CursoMapper;
import pe.edu.university.repository.CursoRepository;
import pe.edu.university.repository.CarreraRepository;
import pe.edu.university.service.CursoService;
import pe.edu.university.util.Constantes;

import java.util.List;

@Service
@Transactional
public class CursoServiceImpl implements CursoService {

    private final CursoRepository repository;
    private final CursoMapper mapper;
    private final CarreraRepository carreraRepository;

    @Autowired
    public CursoServiceImpl(CursoRepository repository, CursoMapper mapper,
            CarreraRepository carreraRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.carreraRepository = carreraRepository;
    }

    @Override
    public CursoDto create(CursoDto dto) {
        Curso entity = mapper.toEntity(dto);

        if (dto.getCarreraId() != null) {
            Carrera carrera = carreraRepository.findById(dto.getCarreraId())
                    .orElseThrow(
                            () -> new IllegalArgumentException(Constantes.CARRERA_NO_ENCONTRADO + dto.getCarreraId()));
            entity.setCarrera(carrera);
        }

        Curso saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public CursoDto update(Long id, CursoDto dto) {
        Curso entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constantes.CURSO_NO_ENCONTRADO + id));
        mapper.updateEntity(dto, entity);

        if (dto.getCarreraId() != null) {
            Carrera carrera = carreraRepository.findById(dto.getCarreraId())
                    .orElseThrow(
                            () -> new IllegalArgumentException(Constantes.CARRERA_NO_ENCONTRADO + dto.getCarreraId()));
            entity.setCarrera(carrera);
        }

        Curso saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public CursoDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.CURSO_NO_ENCONTRADO + id));
    }

    @Override
    public List<CursoDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        Curso e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.CURSO_NO_ENCONTRADO + id));
        repository.delete(e);
    }
}
