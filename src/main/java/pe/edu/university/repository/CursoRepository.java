package pe.edu.university.repository;

import pe.edu.university.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    // example derived query (optional)
    List<Curso> findByCursoId(Long id);
}
