package curso.spring.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import curso.spring.modelo.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	
}
