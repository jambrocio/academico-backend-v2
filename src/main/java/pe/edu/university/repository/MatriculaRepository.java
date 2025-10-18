package pe.edu.university.repository;

import pe.edu.university.entity.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    // example derived query (optional)
    List<Matricula> findByMatriculaId(Long id);
}
