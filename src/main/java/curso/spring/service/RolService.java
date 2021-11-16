package curso.spring.service;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import curso.spring.modelo.Rol;
import curso.spring.repositorio.RolRepository;

@Service
public class RolService {
	
	@Autowired
	private RolRepository repo;
	
	@PostConstruct
	public void cargarRol(){
		Rol r = new Rol(1, "Administrador");
		repo.save(r);
		r = new Rol(2, "Empleado");
		repo.save(r);
		r = new Rol(3, "Cliente");
		repo.save(r);
	}
	
	public List<Rol> getListaRoles(){ 
		return repo.findAll();
	}
	
	public Rol getRolxId(int id) {
		Rol c = repo.getById(id);
		return c;
	}
}

