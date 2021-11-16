package curso.spring.controlador;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import curso.spring.modelo.DetallesPedido;
import curso.spring.modelo.MetodoPago;
import curso.spring.modelo.Producto;
import curso.spring.modelo.Usuario;
import curso.spring.service.MetodoPagoService;
import curso.spring.service.PedidoService;
import curso.spring.service.ProductoService;

@Controller
@RequestMapping("/carritoController")
public class CarritoController {
	
	@Autowired
	ProductoService ps;
	@Autowired
	MetodoPagoService mps;
	@Autowired
	PedidoService pes;
	
	@GetMapping("/addCarrito/{id}")
	public String addCarrito(@PathVariable int id, HttpSession sesion, Model model) {
		ArrayList<DetallesPedido> carrito = (ArrayList<DetallesPedido>)sesion.getAttribute("carrito");
		//double totalPedido = (double)sesion.getAttribute("total");

		boolean existe = false;
		int unidades = 1;
		double totalPedido = 0;
		//double totalPedido =0d;
		
		Producto p = ps.getProductoxId(id);
		if(carrito == null) {
			carrito = new ArrayList<DetallesPedido>();
			sesion.setAttribute("carrito", carrito);
		}
		
		for(DetallesPedido dp: carrito) {
			if(dp.getIdProducto() == (p.getId())) {	

				//if(p.getStock()>0) {
					dp.setUnidades(dp.getUnidades()+1);
					dp.setTotal(dp.getUnidades()*dp.getPrecio_unidad());
					p.setStock(p.getStock()-1);
					totalPedido += pes.calcularTotalDetalles(carrito);
					existe = true;
				/*}else {
					p.setStock(0);
					model.addAttribute("mensaje", "No hay stock del producto");
				}*/

						
				//totalPedido += dp.getTotal();
				break;
			}		
		}
		
		if(!existe) {
			DetallesPedido dp = new DetallesPedido(p.getId(), p.getNombre(), p.getPrecio(), unidades, p.getImpuesto(), (unidades*p.getPrecio()));			
			carrito.add(dp);		
			p.setStock(p.getStock()-1); 
			totalPedido += pes.calcularTotalDetalles(carrito);
			//totalPedido += dp.getTotal();
		}	
		ps.editProducto(p);
		sesion.setAttribute("total", totalPedido);
		//sesion.setAttribute("total", totalPedido);
		//sesion.setAttribute("carrito", carrito);
		
		return "redirect:/";
	}
	
	@GetMapping("/deleteCarrito/{id}")
	public String deleteCarrito(@PathVariable int id, HttpSession sesion) {
		ArrayList<DetallesPedido> carrito = (ArrayList<DetallesPedido>)sesion.getAttribute("carrito");		
		Producto p = ps.getProductoxId(id);
		double totalPedido = (double) sesion.getAttribute("total");
		
		for(DetallesPedido dp: carrito) {
			if(dp.getIdProducto() == (p.getId())) {	
				if(dp.getUnidades()>1) {
					dp.setUnidades(dp.getUnidades()-1);
					dp.setTotal(dp.getTotal()-dp.getPrecio_unidad());
					totalPedido -= dp.getPrecio_unidad();
					//sesion.setAttribute("total", totalPedido);
				}
				else {
					carrito.remove(dp);
					//totalPedido = 0d;
					totalPedido -= dp.getPrecio_unidad();
					sesion.setAttribute("total", 0);
					break;
				}
			}
		}	
		
		//sesion.setAttribute("carrito", carrito);
		sesion.setAttribute("total", totalPedido);
		
		return "redirect:/carritoController/carrito";
	}
	
	@GetMapping("/carrito")
	public String mostrarCarrito(HttpSession sesion) {
		//double total = (double)sesion.getAttribute("total");
		//model.addAttribute("total", total);
		//sesion.setAttribute("lista", productdao.getListaProductos());
		//Double totalPedido = (Double)sesion.getAttribute("total");
//		if(totalPedido == null) {
//			
//		}
		return "carrito";
	}
	
	/*
	@GetMapping("/realizar_pedido_tarjeta")
	public String realizarPedidoTarjeta(HttpSession session) {
		
		session.setAttribute("pago", mps.getMetodoPagoxId(1));
		return "redirect:/pedido/registrar_pedido/";	
	}
	
	@GetMapping("/realizar_pedido_paypal")
	public String realizarPedidoPaypal(HttpSession session) {
		
		session.setAttribute("pago", mps.getMetodoPagoxId(2));
		return "redirect:/pedido/registrar_pedido/";
		
	}*/
	
	@GetMapping("/comprar")
	public String comprar(HttpSession session, Model model) {
		model.addAttribute("metodoPago", new MetodoPago());
		Usuario u = (Usuario) session.getAttribute("usuario");
		
		if(u.getId() == 0) {
			return "usuario/login";
		}else {
			return "comprar";
		}
	}
}
