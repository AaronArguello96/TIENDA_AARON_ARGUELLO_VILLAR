package curso.spring.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import curso.spring.modelo.DetallesPedido;
import curso.spring.repositorio.DetallesPedidoRepository;

@Service
public class DetallesPedidoService {
	@Autowired
	private DetallesPedidoRepository repo;
	
	@PostConstruct
	public void cargarPedido(){

	}
	
	public List<DetallesPedido> getListaDetalles_pedido(){ 
		return repo.findAll();
	}
	
	public void addDetalles_pedido(DetallesPedido detalles_pedido) {

		repo.save(detalles_pedido);
	}
	
	public void delDetalles_pedido(int id) {
		DetallesPedido dp = repo.getById(id);
		repo.delete(dp);
	}
	
	public DetallesPedido getDetalles_pedidoxId(int id) {
		DetallesPedido dp = repo.getById(id);
		return dp;
	}
	
	public void editDetalles_pedido(DetallesPedido detalles_pedido){
		repo.save(detalles_pedido);
	}
	
	public ArrayList<DetallesPedido> getDetalles_pedidoxIdPedido(int id) {
		ArrayList<DetallesPedido> lista = repo.findByIdPedido(id);
		return lista;
	}

}
