package curso.spring.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import curso.spring.modelo.MetodoPago;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {
	MetodoPago findById(int id);
}
