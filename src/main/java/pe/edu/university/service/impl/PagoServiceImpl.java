package pe.edu.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.university.dto.PagoDto;
import pe.edu.university.entity.Pago;
import pe.edu.university.exception.ResourceNotFoundException;
import pe.edu.university.mapper.PagoMapper;
import pe.edu.university.repository.PagoRepository;
import pe.edu.university.service.PagoService;
import pe.edu.university.util.Constantes;

import java.util.List;

@Service
@Transactional
public class PagoServiceImpl implements PagoService {

    private final PagoRepository repository;
    private final PagoMapper mapper;

    @Autowired
    public PagoServiceImpl(PagoRepository repository, PagoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PagoDto create(PagoDto dto) {
        Pago e = mapper.toEntity(dto);
        Pago saved = repository.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public PagoDto update(Long id, PagoDto dto) {
        Pago existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.PAGO_NO_ENCONTRADO + id));
        // simple field updates (mapper could be more advanced)
        Pago updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public PagoDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.PAGO_NO_ENCONTRADO + id));
    }

    @Override
    public List<PagoDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        Pago e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constantes.PAGO_NO_ENCONTRADO + id));
        repository.delete(e);
    }
}
