package curso.spring.controlador;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import curso.spring.modelo.Usuario;
import curso.spring.service.ProductoService;
import curso.spring.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioService us; //= new UsuarioDAO();
	@Autowired
	ProductoService ps;
	
	@GetMapping("/listar")
	public String listarUsuarios(Model model) {
		model.addAttribute("lista", us.getListaUsuarios());
		return "usuario/lista_usuarios";
	}
	
	//+------GESTION DE CLIENTES (CREACION, EDICION Y ELIMINACION POR EL EMPLEADO)------+
	@GetMapping("/listarXrolCliente")
	public String listarUsuariosxRolCliente(Model model) {
		model.addAttribute("lista", us.listaUsuarioxRol(3));
		return "usuario/lista_usuarios";
	}
	
	@GetMapping("/crear")
	public String formCrearUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/crear_usuario";
	}
	
	@PostMapping("/crear/submit")
	public String crear(@ModelAttribute Usuario usuario, String clave) {
		usuario.setClave(us.encriptarPassword(clave));
		usuario.setIdrol(3);
		us.addUsuario(usuario);	
		return "redirect:/usuario/listarXrolCliente";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable int id) {
		us.delUsuario(id);
		return "redirect:/usuario/listarXrolCliente";
	}
	
	@GetMapping("/editar/{id}")
	public String editarUsuarioForm(@PathVariable int id, Model model) {
		Usuario u = us.getUsuarioxId(id);
		model.addAttribute("usuario", u);
		return "usuario/edit_usuario";
	}
	
	@PostMapping("/editar/submit")
	public String editarUsuario(@ModelAttribute Usuario usuario, String clave) {
		usuario.setClave(us.encriptarPassword(clave));
		us.editUsuario(usuario);
		return "redirect:/usuario/listarXrolCliente";
	}
	
	//+-------------------------------------------------------------------------------+
	//+------GESTION DE EMPLEADOS (CREACION, EDICION Y ELIMINACION POR EL ADMIN)------+
	@GetMapping("/listarXrolEmpleado")
	public String listarUsuariosxRolEmpleado(Model model) {
		model.addAttribute("lista", us.listaUsuarioxRol(2));
		return "usuario/lista_empleados";
	}

	@GetMapping("/crearEmpleado")
	public String formCrearUsuarioEmpleado(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/crear_empleado";
	}
	
	@PostMapping("/crearEmpleado/submit")
	public String crearEmpleado(@ModelAttribute Usuario usuario, String clave) {
		usuario.setClave(us.encriptarPassword(clave));
		usuario.setIdrol(2);
		us.addUsuario(usuario);	
		return "redirect:/usuario/listarXrolEmpleado";
	}
	
	@GetMapping("/eliminarEmpleado/{id}")
	public String eliminarEmpleado(@PathVariable int id) {
		us.delUsuario(id);
		return "redirect:/usuario/listarXrolEmpleado";
	}
	
	@GetMapping("/editarEmpleado/{id}")
	public String editarEmpleadoForm(@PathVariable int id, Model model) {
		Usuario u = us.getUsuarioxId(id);
		model.addAttribute("usuario", u);
		return "usuario/edit_empleado";
	}
	
	@PostMapping("/editarEmpleado/submit")
	public String editarEmpleado(@ModelAttribute Usuario usuario, String clave) {
		usuario.setClave(us.encriptarPassword(clave));
		us.editUsuario(usuario);
		return "redirect:/usuario/listarXrolEmpleado";
	}
	
	//+-------------------------------------------------------------------------------+
	
	@GetMapping("/formLogin")
	public String formulario(Model model, HttpSession session) {
			
		model.addAttribute("usuario", new Usuario());
		return "usuario/login";

	}
	
	@PostMapping("/login")
	public String logarse(Model model, @ModelAttribute Usuario usuario, HttpSession sesion) {
		
		if(us.comprobarLogin(usuario)) {
			Usuario user = us.getUsuarioxEmail(usuario.getEmail());
			sesion.setAttribute("usuario", user);
			model.addAttribute("lista", ps.getListaProductos());
			return "redirect:/";
		}else {
			model.addAttribute("mensaje", "El usuario y/o la contraseña no son correctos.");
			return "usuario/login";
		}
	}	

	@GetMapping("/formAlta")
	public String formularioAlta(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/alta";
	}
	
	@PostMapping("/alta")
	public String alta(Model model, String clave, String clave2, @Valid @ModelAttribute Usuario usuario, HttpSession sesion, BindingResult bindingResult) {
		if(us.comprobarEmail(usuario) && !bindingResult.hasErrors()) {
			if(clave.equals(clave2)){
				usuario.setClave(us.encriptarPassword(clave));
				usuario.setIdrol(3);
				us.addUsuario(usuario);
				sesion.setAttribute("usuario", usuario);
				return "redirect:/";
			}else {
				model.addAttribute("mensaje", "Las contraseñas no coinciden.");
				model.addAttribute("usuario", new Usuario());
				return "usuario/alta";
			}	
		}else {
			model.addAttribute("mensaje", "El email introducido ya está registrado");
			model.addAttribute("usuario", new Usuario());
			return "usuario/alta";
		}
	}
	
	@GetMapping("/logout")
	public String desloguearse(HttpSession sesion) {
		sesion.invalidate();
		return("redirect:/");
	}
	//+--------------------EDICIÓN DEL PERFIL POR EL USUARIO------------------------+
	@GetMapping("/perfil/{id}")
	public String perfil(@PathVariable int id, Model model, HttpSession sesion) {
		Usuario u = us.getUsuarioxId(id);
		model.addAttribute("usuario", u);
		return("usuario/perfil");
	}
	
	@PostMapping("/perfil/submit/{id}")
	public String editarPerfil(@PathVariable int id, @ModelAttribute Usuario usuario) {
		Usuario u = us.getUsuarioxId(id);
		us.editUsuario(usuario);
		return "usuario/perfil";
	}
	
	//+-----------------------------------------------------------------------------+
	
	/*
	@GetMapping("/formModificarPassword")
	public String formModificarPassword() {
		return "usuario/modificarPassword";

	}
	
	@PostMapping("/modificarPassword")
	public String modificarPassword(Model model, String clave, String clave2, HttpSession sesion) {
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		if(clave.equals(usuario.getClave())) {
			usuario.setClave(clave2);
			return "usuario/perfil";
		}else {
			model.addAttribute("mensaje", "La contraseña antigua no es correcta.");
			return "usuario/modificarPassword";
		}
	}
	*/
}
