package pe.edu.university.repository;

import pe.edu.university.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    // example derived query (optional)
    List<Estudiante> findByEstudianteId(Long id);
}
