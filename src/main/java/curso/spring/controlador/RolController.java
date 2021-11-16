package curso.spring.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import curso.spring.service.RolService;

@Controller
@RequestMapping("/rol")
public class RolController {
	
	@Autowired
	RolService rs;
	
	@GetMapping("/listar")
	public String listarRoles(Model model) {
		model.addAttribute("lista", rs.getListaRoles());
		return "rol/lista_roles";
	}

}
