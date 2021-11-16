package curso.spring.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import curso.spring.modelo.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	Producto findByNombre(String nombre);
	//List<Producto> findById_categoria(int id_categoria);
	Producto findByPrecio(double precio);
}
