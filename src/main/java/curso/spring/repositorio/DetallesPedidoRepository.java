package curso.spring.repositorio;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import curso.spring.modelo.DetallesPedido;

public interface DetallesPedidoRepository extends JpaRepository<DetallesPedido, Integer> {
	ArrayList<DetallesPedido> findByIdPedido(int id);
}
