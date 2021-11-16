package curso.spring.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import curso.spring.modelo.DetallesPedido;
import curso.spring.modelo.Pedido;
import curso.spring.repositorio.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@PostConstruct
	public void cargarPedido(){
		
	}
	
	public List<Pedido> getListaPedidos(){ 
		return repo.findAll();
	}
	
	public List<Pedido> getListaPedidosxIdUsuario(int id){ 
		List<Pedido> lista = repo.findByIdUsuario(id);
		return lista;
	}
	
	public Pedido addPedido(Pedido pedido){
		return repo.save(pedido);
	}
	
	public void delPedido(int id) {
		Pedido p = repo.getById(id);
		repo.delete(p);
	}
	
	public Pedido getPedidoxId(int id) {
		Pedido p = repo.findById(id).get(0);
		//Pedido p = repo.getById(id);
		return p;
	}
	
	public void editPedido(Pedido pedido){
		repo.save(pedido);
	}
	
	public void calcularTotal(ArrayList<DetallesPedido> detalles) {
		Pedido p = new Pedido();
		p.setTotal(calcularTotalDetalles(detalles));
		
	}
	public static Double calcularTotalDetalles(ArrayList<DetallesPedido> detalles) {
		Double total = 0d;
		
		for(DetallesPedido d: detalles)
			total += d.getTotal();
		
		return total;
	}	
}
