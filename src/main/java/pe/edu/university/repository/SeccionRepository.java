package pe.edu.university.repository;

import pe.edu.university.entity.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeccionRepository extends JpaRepository<Seccion, Long> {
    // example derived query (optional)
    @Query("SELECT e FROM Seccion e WHERE e.seccionId = :id")
    Seccion findBySeccionId(Long id);
}
