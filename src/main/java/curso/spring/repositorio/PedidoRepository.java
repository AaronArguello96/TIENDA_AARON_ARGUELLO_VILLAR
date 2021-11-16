package curso.spring.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import curso.spring.modelo.Pedido;
import curso.spring.modelo.Usuario;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	List<Pedido> findById(int id);
	
	Pedido getById(int id);
	List<Pedido> findByIdUsuario(int idusuario);
}
