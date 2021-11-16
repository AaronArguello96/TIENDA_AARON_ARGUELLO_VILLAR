package curso.spring.service;

import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import curso.spring.modelo.Producto;
import curso.spring.repositorio.ProductoRepository;

@Service
public class ProductoService {
	
	@Autowired
	private ProductoRepository repo;
	
	private static Logger logger = LogManager.getLogger(ProductoService.class.getName());
	
	@PostConstruct
	public void cargarProductos(){
		Producto p = new Producto(1, 2, "Back4Blood", "Juego de zombies", 45, 50, 21, "/img/b4b.jpg");
		repo.save(p);
		p = new Producto(2, 2, "Call of Duty Vanguard", "Shooter", 60, 50, 21, "/img/cod.jpg");
		repo.save(p);
		p = new Producto(3, 1, "FarCry 6", "Mundo abierto", 65, 50, 21, "/img/fc6.jpg");
		repo.save(p);
		p = new Producto(4, 1, "GTA V", "Mundo abierto", 15, 50, 21, "/img/gtav.jpg");
		repo.save(p);
		p = new Producto(5, 1, "Cyberpunk 2077", "Mundo abierto", 20, 50, 21, "/img/cyberpunk.jpg");
		repo.save(p);
		p = new Producto(6, 3, "Oddworld Soulstorm", "Plataformas", 30, 50, 21, "/img/oddworld.jpg");
		repo.save(p);
		p = new Producto(7, 4, "Formula 1 2021", "Simulador de conducción", 40, 50, 21, "/img/f1.jpg");
		repo.save(p);
		p = new Producto(8, 2, "Battlefield 2042", "Shooter", 35, 50, 21, "/img/battlefield.jpg");
		repo.save(p);
		p = new Producto(9, 1, "Resident Evil Village", "Mundo abierto", 45, 50, 21, "/img/rev.jpg");
		repo.save(p);
		
		logger.info("Creación e insercción de productos en la BD");
	}
	
	public List<Producto> getListaProductos(){ 
		
		return repo.findAll();
	}
	
	public void addProducto(Producto producto) {
		
		repo.save(producto);
		logger.info("Se ha creado un nuevo producto");
	}
	
	public void delProducto(int id) {
		Producto p = repo.getById(id);
		repo.delete(p);
		logger.info("Se ha eliminado un producto");
	}
	
	public Producto getProductoxId(int id) {
		Producto p = repo.getById(id);
		return p;
	}
	/*
	public List<Producto> getListaProductosxCategoria(int id_categoria){
		List<Producto> listaxcategoria= repo.findById_categoria(id_categoria);
		return listaxcategoria;
	}
	
	
	public List<Producto> getProductosxPrecio(double precio){
		List<Producto> listaxprecio= (List<Producto>) repo.findByPrecio(precio);
		return listaxprecio;
	}*/
	
	public void editProducto(Producto producto){
		repo.save(producto);
		logger.info("Se ha editado un producto");
	}
	
}
