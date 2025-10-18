package pe.edu.university.repository;

import pe.edu.university.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    // example derived query (optional)
    List<Pago> findByPagoId(Long id);
}
