package pe.edu.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.university.dto.CalificacionDto;
import pe.edu.university.service.CalificacionService;

import java.util.List;

@RestController
@RequestMapping("/api/calificacions")
@RequiredArgsConstructor
public class CalificacionController {

    @Autowired
    CalificacionService service;

    @PostMapping
    public ResponseEntity<CalificacionDto> create(@RequestBody CalificacionDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalificacionDto> update(@PathVariable Long id, @RequestBody CalificacionDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalificacionDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<CalificacionDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
