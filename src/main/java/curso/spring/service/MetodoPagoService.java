package curso.spring.service;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import curso.spring.modelo.MetodoPago;
import curso.spring.repositorio.MetodoPagoRepository;

@Service
public class MetodoPagoService {
	@Autowired
	private MetodoPagoRepository repo;
	
	@PostConstruct
	public void cargarMetodoPago(){
		MetodoPago mp = new MetodoPago(1, "Tarjeta");
		repo.save(mp);
		mp = new MetodoPago(2, "Paypal");
		repo.save(mp);

	}
	
	public List<MetodoPago> getListaMetodoPago(){ 
		return repo.findAll();
	}
	
	public MetodoPago getMetodoPagoxId(int id) {
		MetodoPago mp = repo.getById(id);
		return mp;
	}	
}
