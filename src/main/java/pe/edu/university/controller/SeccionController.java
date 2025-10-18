package pe.edu.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.university.dto.SeccionDto;
import pe.edu.university.service.SeccionService;

import java.util.List;

@RestController
@RequestMapping("/api/seccions")
@RequiredArgsConstructor
public class SeccionController {

    @Autowired
    SeccionService service;

    @PostMapping
    public ResponseEntity<SeccionDto> create(@RequestBody SeccionDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeccionDto> update(@PathVariable Long id, @RequestBody SeccionDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeccionDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<SeccionDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
