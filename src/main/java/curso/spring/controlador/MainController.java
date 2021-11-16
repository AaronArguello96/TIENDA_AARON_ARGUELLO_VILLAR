package curso.spring.controlador;


import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import curso.spring.modelo.DetallesPedido;
import curso.spring.modelo.Usuario;
import curso.spring.service.ConfiguracionService;
import curso.spring.service.ProductoService;
import curso.spring.service.UsuarioService;


@Controller
@RequestMapping("")
public class MainController {
	
	@Autowired
	UsuarioService us; //= new UsuarioDAO();
	@Autowired
	ProductoService ps; //= new ProductoDAO();
	@Autowired
	ConfiguracionService cs;
	private static Logger logger = LogManager.getLogger(MainController.class.getName());
	
	@GetMapping("")
	public String listarProductos(Model model, HttpSession sesion) {
		
		logger.info("Comienza la ejecución de la aplicación");
		model.addAttribute("lista", ps.getListaProductos());
		ArrayList<DetallesPedido> carrito = (ArrayList<DetallesPedido>)sesion.getAttribute("carrito");
		if(carrito==null) {
			carrito = new ArrayList<DetallesPedido>();
			sesion.setAttribute("carrito", carrito);
			sesion.setAttribute("total", 0);
			logger.info("Se crea el carrito vacío");
		}
		Usuario usuario = (Usuario)sesion.getAttribute("usuario");
		if(usuario == null) {
			logger.info("Si el usuario no está logueado se crea una sesión anónima");
			sesion.setAttribute("usuario", new Usuario());
		}
		return "index";
	}	
}
