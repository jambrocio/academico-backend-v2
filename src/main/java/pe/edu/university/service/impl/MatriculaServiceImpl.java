package pe.edu.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.MatriculaDto;
import pe.edu.university.entity.Estudiante;
import pe.edu.university.entity.Seccion;
import pe.edu.university.entity.Matricula;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.MatriculaMapper;
import pe.edu.university.repository.EstudianteRepository;
import pe.edu.university.repository.MatriculaRepository;
import pe.edu.university.repository.SeccionRepository;
import pe.edu.university.service.MatriculaService;
import pe.edu.university.util.Constantes;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository repository;
    private final EstudianteRepository estudianteRepository;
    private final SeccionRepository seccionRepository;
    private final MatriculaMapper mapper;

    @Autowired
    public MatriculaServiceImpl(MatriculaRepository repository, EstudianteRepository estudianteRepository,
            SeccionRepository seccionRepository, MatriculaMapper mapper) {
        this.repository = repository;
        this.estudianteRepository = estudianteRepository;
        this.seccionRepository = seccionRepository;
        this.mapper = mapper;
    }

    @Override
    public MatriculaDto create(MatriculaDto dto) {
        // create entity from DTO and save
        Matricula e = mapper.toEntity(dto);
        Matricula saved = repository.save(e);
        if (saved == null) {
            throw new ResourceNotFoundException("Error al crear la Matricula");
        }

        // resolve Estudiante: prefer the loaded relation, otherwise fetch by id
        Estudiante estudiante = null;
        if (saved.getEstudianteId() != null) {
            estudiante = estudianteRepository.findByEstudianteId(saved.getEstudianteId());
            saved.setEstudiante(estudiante);
        }

        Seccion seccion = null;
        if (saved.getSeccionId() != null) {
            seccion = seccionRepository.findBySeccionId(saved.getSeccionId());
            saved.setSeccion(seccion);
        }

        // build complete DTO for response and external service
        MatriculaDto resultDto = mapper.toDto(saved, estudiante);

        return resultDto;
    }

    @Override
    public MatriculaDto update(Long id, MatriculaDto dto) {
        // load existing
        Matricula existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.MATRICULA_NO_ENCONTRADO + id));

        // update simple fields from DTO
        existing.setFechaMatricula(dto.getFechaMatricula());
        existing.setEstado(dto.getEstado());
        existing.setCosto(dto.getCosto());
        existing.setMetodoPago(dto.getMetodoPago());

        // update FK ids and relation objects
        existing.setEstudianteId(dto.getEstudianteId());
        if (dto.getEstudianteId() != null) {
            // try to fetch full Estudiante, otherwise attach a minimal reference
            Estudiante est = estudianteRepository.findByEstudianteId(dto.getEstudianteId());
            if (est != null)
                existing.setEstudiante(est);
            else {
                Estudiante ref = new Estudiante();
                ref.setEstudianteId(dto.getEstudianteId());
                existing.setEstudiante(ref);
            }
        }

        existing.setSeccionId(dto.getSeccionId());
        if (dto.getSeccionId() != null) {
            Seccion sec = new Seccion();
            sec.setSeccionId(dto.getSeccionId());
            existing.setSeccion(sec);
        }

        Matricula updated = repository.save(existing);

        Estudiante estudiante = null;
        if (updated.getEstudiante() != null)
            estudiante = updated.getEstudiante();
        else if (updated.getEstudianteId() != null)
            estudiante = estudianteRepository.findByEstudianteId(updated.getEstudianteId());

        return mapper.toDto(updated);
    }

    @Override
    public MatriculaDto findById(Long id) {
        Matricula e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.MATRICULA_NO_ENCONTRADO + id));
        Estudiante estudiante = null;
        if (e.getEstudiante() != null) {
            estudiante = e.getEstudiante();
        } else if (e.getEstudianteId() != null) {
            estudiante = estudianteRepository.findByEstudianteId(e.getEstudianteId());
        }
        return mapper.toDto(e);
    }

    @Override
    public List<MatriculaDto> findAll() {
        return repository.findAll().stream().map(e -> {
            Estudiante estudiante = null;
            if (e.getEstudiante() != null) {
                estudiante = e.getEstudiante();
            } else if (e.getEstudianteId() != null) {
                estudiante = estudianteRepository.findByEstudianteId(e.getEstudianteId());
            }
            return mapper.toDto(e);
        }).toList();
    }

    @Override
    public void delete(Long id) {
        Matricula e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.MATRICULA_NO_ENCONTRADO + id));
        repository.delete(e);
    }
}
