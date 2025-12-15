package pe.edu.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.university.dto.PrerrequisitoDto;
import pe.edu.university.service.PrerrequisitoService;

import java.util.List;

@RestController
@RequestMapping("/api/prerrequisitos")
public class PrerrequisitoController {

    private final PrerrequisitoService service;

    @Autowired
    public PrerrequisitoController(PrerrequisitoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PrerrequisitoDto> create(@RequestBody PrerrequisitoDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrerrequisitoDto> update(@PathVariable Long id, @RequestBody PrerrequisitoDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrerrequisitoDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PrerrequisitoDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
