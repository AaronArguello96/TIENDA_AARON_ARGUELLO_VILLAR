package curso.spring.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import curso.spring.modelo.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
	Rol findByRol(String rol);
}

