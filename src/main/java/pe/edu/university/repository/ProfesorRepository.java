package pe.edu.university.repository;

import pe.edu.university.entity.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    // example derived query (optional)
    List<Profesor> findByProfesorId(Long id);
}
