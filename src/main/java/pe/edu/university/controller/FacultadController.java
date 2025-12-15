package pe.edu.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.university.dto.FacultadDto;
import pe.edu.university.security.ValidateToken;
import pe.edu.university.service.FacultadService;

import java.util.List;

@RestController
@RequestMapping("/api/facultads")
public class FacultadController {

    private final FacultadService service;

    @Autowired
    public FacultadController(FacultadService service) {
        this.service = service;
    }

    @PostMapping
    @ValidateToken
    public ResponseEntity<FacultadDto> create(@RequestBody FacultadDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @ValidateToken
    public ResponseEntity<FacultadDto> update(@PathVariable Long id, @RequestBody FacultadDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultadDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<FacultadDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    @ValidateToken
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
