package curso.spring.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import curso.spring.modelo.Configuracion;
import java.util.List;

public interface ConfiguracionRepository extends JpaRepository<Configuracion, Integer> {
	//Crear una query para recuperar el valor de numfactura
	List<Configuracion> findByClave(String clave);
	//Cada vez que se hace una llamada tiene que hacer un update (save) de ese valor
}