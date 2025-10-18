package pe.edu.university.repository;

import pe.edu.university.entity.Prerrequisito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrerrequisitoRepository extends JpaRepository<Prerrequisito, Long> {
    // example derived query (optional)
    List<Prerrequisito> findByPrerrequisitoId(Long id);
}
