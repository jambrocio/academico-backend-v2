package pe.edu.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.university.dto.ProfesorDto;
import pe.edu.university.service.ProfesorService;

import java.util.List;

@RestController
@RequestMapping("/api/profesors")
@RequiredArgsConstructor
public class ProfesorController {

    private final ProfesorService service;

    @PostMapping
    public ResponseEntity<ProfesorDto> create(@RequestBody ProfesorDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfesorDto> update(@PathVariable Long id, @RequestBody ProfesorDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfesorDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
