package curso.spring.service;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import curso.spring.modelo.Configuracion;
import curso.spring.repositorio.ConfiguracionRepository;

@Service
public class ConfiguracionService {
	
	@Autowired
	private ConfiguracionRepository repo;
	
	@PostConstruct
	public void cargarConfiguracion(){
		Configuracion c = new Configuracion(Configuracion.NUM_FACTURA, "2021-0002", "texto");
		repo.save(c);
		c = new Configuracion(Configuracion.NOMBRE, "Tienda.es", "texto");
		repo.save(c);
		c = new Configuracion(Configuracion.DIRECCION, "Zamora", "texto");
		repo.save(c);
		c = new Configuracion(Configuracion.CIF, "B-76365789", "texto");
		repo.save(c);
	}
	
	public List<Configuracion> getListaConfiguraciones(){ 
		return repo.findAll();
	}
	
	public Configuracion getConfiguracionxId(int id) {
		Configuracion c = repo.getById(id);
		return c;
	}
	
	public Configuracion getListaByClave(String clave) {
	
        return repo.findByClave(clave).get(0);
    }
	
	public void edit(Configuracion configuracion) {
		repo.save(configuracion);
	}
}
