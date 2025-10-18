package pe.edu.university.repository;

import pe.edu.university.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    // example derived query (optional)
    List<Carrera> findByCarreraId(Long id);
}
