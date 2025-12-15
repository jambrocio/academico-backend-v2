package pe.edu.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.CarreraDto;
import pe.edu.university.entity.Carrera;
import pe.edu.university.entity.Facultad;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.CarreraMapper;
import pe.edu.university.repository.CarreraRepository;
import pe.edu.university.repository.FacultadRepository;
import pe.edu.university.service.CarreraService;
import pe.edu.university.util.Constantes;

import java.util.List;

@Service
@Transactional
public class CarreraServiceImpl implements CarreraService {

    private final CarreraRepository repository;
    private final CarreraMapper mapper;
    private final FacultadRepository facultadRepository;

    @Autowired
    public CarreraServiceImpl(CarreraRepository repository, CarreraMapper mapper,
            FacultadRepository facultadRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.facultadRepository = facultadRepository;
    }

    @Override
    public CarreraDto create(CarreraDto dto) {
        Carrera e = mapper.toEntity(dto);

        // Asignar Facultad si se proporcionó facultadId
        if (dto.getFacultadId() != null) {
            Facultad facultad = facultadRepository.findById(dto.getFacultadId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            Constantes.FACULTAD_NO_ENCONTRADO + dto.getFacultadId()));
            e.setFacultad(facultad);
        }

        Carrera saved = repository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public CarreraDto update(Long id, CarreraDto dto) {
        Carrera existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.CARRERA_NO_ENCONTRADO + id));
        if (dto.getNombre() != null)
            existing.setNombre(dto.getNombre());
        if (dto.getDuracionSemestres() != null)
            existing.setDuracionSemestres(dto.getDuracionSemestres());

        if (dto.getFacultadId() != null) {
            Facultad facultad = facultadRepository.findById(dto.getFacultadId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            Constantes.FACULTAD_NO_ENCONTRADO + dto.getFacultadId()));
            existing.setFacultad(facultad);
        }

        Carrera updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public CarreraDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.CARRERA_NO_ENCONTRADO + id));
    }

    @Override
    public List<CarreraDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        Carrera e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.CARRERA_NO_ENCONTRADO + id));
        repository.delete(e);
    }
}
