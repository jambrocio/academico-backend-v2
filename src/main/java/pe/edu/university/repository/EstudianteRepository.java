package pe.edu.university.repository;

import pe.edu.university.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    // JPQL query to obtain Estudiante by its PK (estudianteId)
    @Query("SELECT e FROM Estudiante e WHERE e.estudianteId = :id")
    Estudiante findByEstudianteId(@Param("id") Long id);
}
