package curso.spring.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import curso.spring.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	Usuario findByNombre(String nombre);
	@Query(value="select * from Usuario where email=?1 and clave=?2", nativeQuery = true)
	List<Usuario> buscarUsuarioLogin(String email, String clave);
	//@Query(value="select * from Usuario where email=?1", nativeQuery = true)
	//List<Usuario> buscarEmailUsuario(String email);
	Usuario findByEmail(String email);
	List<Usuario> findByIdrol(int idrol);
}
