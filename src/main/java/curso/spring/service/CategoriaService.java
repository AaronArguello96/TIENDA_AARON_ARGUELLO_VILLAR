package curso.spring.service;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import curso.spring.modelo.Categoria;
import curso.spring.repositorio.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	@PostConstruct
	public void cargarCategoria(){
		Categoria c = new Categoria(1, "Mundo abierto", "Juego de mundo abierto");
		repo.save(c);
		c = new Categoria(2, "FPS", "First Person Shooter (Juego de disparos en primera persona)");
		repo.save(c);
		c = new Categoria(3, "Plataformas", "Juego de plataformas");
		repo.save(c);
		c = new Categoria(4, "Simulador", "Simulador de conducción");
		repo.save(c);
	}
	
	public List<Categoria> getListaCategorias(){ 
		return repo.findAll();
	}
	
	public void addCategoria(Categoria categoria) {

		repo.save(categoria);
	}
	
	public void delCategoria(int id) {
		Categoria c = repo.getById(id);
		repo.delete(c);
	}
	
	public Categoria getCategoriaxId(int id) {
		Categoria c = repo.getById(id);
		return c;
	}
	
	public void editCategoria(Categoria categoria){
		repo.save(categoria);
	}
	
}