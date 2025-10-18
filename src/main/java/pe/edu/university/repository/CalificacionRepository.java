package pe.edu.university.repository;

import pe.edu.university.entity.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    // example derived query (optional)
    List<Calificacion> findByCalificacionId(Long id);
}
