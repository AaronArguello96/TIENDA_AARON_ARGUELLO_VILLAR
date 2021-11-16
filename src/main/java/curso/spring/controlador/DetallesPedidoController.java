package curso.spring.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import curso.spring.modelo.DetallesPedido;
import curso.spring.service.DetallesPedidoService;
import curso.spring.service.ProductoService;

@Controller
@RequestMapping("/detalles_pedido")
public class DetallesPedidoController {

	@Autowired
	DetallesPedidoService dps;
	@Autowired
	ProductoService ps;

	@GetMapping("/listar")
	public String listarDetalles_pedido(Model model) {
		model.addAttribute("lista", dps.getListaDetalles_pedido());
		return "detalles_pedido/lista_detalles_pedido";
	}

	@GetMapping("/crear")
	public String formCrearDetalles_pedido(Model model) {
		model.addAttribute("detalles_pedido", new DetallesPedido());
		return "detalles_pedido/nuevo_detalles_pedido";
	}

	@PostMapping("/agregar")
	public String agregarDetalles_pedido(Model model, DetallesPedido detalles_pedido) {
		dps.addDetalles_pedido(detalles_pedido);
		return "redirect:/detalles_pedido/lista_detalles_pedido";
	}

	@PostMapping("/crear/submit")
	public String crear(@ModelAttribute DetallesPedido detalles_pedido) {
		dps.addDetalles_pedido(detalles_pedido);
		return "redirect:/detalles_pedido/lista_detalles_pedido";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable int id) {
		dps.delDetalles_pedido(id);
		return "redirect:/detalles_pedido/lista_detalles_pedido";
	}

	@GetMapping("/editar/{id}")
	public String editarDetalles_pedidoForm(@PathVariable int id, Model model) {
		DetallesPedido dp = dps.getDetalles_pedidoxId(id);
		model.addAttribute("detalles_pedido", dp);
		return "detalles_pedido/edit_detalle_pedido";
	}

	@PostMapping("/editar/submit")
	public String editarCategoria(@ModelAttribute DetallesPedido detalles_pedido) {
		dps.editDetalles_pedido(detalles_pedido);
		return "redirect:/detalles_pedido/lista_detalles_pedido";
	}
	/*
	@GetMapping("/addCarrito/{id}")
	public String addCarrito(@PathVariable int id, HttpSession sesion) {
		ArrayList<Detalles_pedido> carrito = (ArrayList<Detalles_pedido>)sesion.getAttribute("carrito");
		boolean existe = false;
		int unidades = 1;
		Producto p = ps.getProductoxId(id);
		if(carrito == null) {
			carrito = new ArrayList<Detalles_pedido>();
		}
		for(Detalles_pedido dp: carrito) {
			if(dp.getId_producto() == (p.getId())) {	
				dp.setUnidades(dp.getUnidades()+1);
				dp.setTotal(dp.getUnidades()*dp.getPrecio_unidad());
				existe = true;
				break;
			}

		}
		if(!existe) {
			Detalles_pedido dp = new Detalles_pedido(1, 1, p.getId(), p.getNombre(), p.getPrecio(), unidades, p.getImpuesto(), (unidades*p.getPrecio()));
			carrito.add(dp);
		}	
		sesion.setAttribute("carrito", carrito);
		
		return "redirect:/";
	}
	
	@GetMapping("/carrito")
	public String mostrarCarrito(Model model, HttpSession sesion) {
		//sesion.setAttribute("lista", productdao.getListaProductos());
		return "carrito";
	}*/
}
