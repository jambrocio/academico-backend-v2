package pe.edu.university.repository;

import pe.edu.university.entity.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeccionRepository extends JpaRepository<Seccion, Long> {
    // example derived query (optional)
    List<Seccion> findBySeccionId(Long id);
}
