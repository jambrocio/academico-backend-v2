package pe.edu.university.repository;

import pe.edu.university.entity.Facultad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FacultadRepository extends JpaRepository<Facultad, Long> {
    // example derived query (optional)
    List<Facultad> findByFacultadId(Long id);
}
