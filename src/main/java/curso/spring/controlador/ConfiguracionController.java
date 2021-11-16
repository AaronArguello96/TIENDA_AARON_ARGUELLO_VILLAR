package curso.spring.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import curso.spring.modelo.Configuracion;
import curso.spring.service.ConfiguracionService;

@Controller
@RequestMapping("/configuracion")
public class ConfiguracionController {
	@Autowired
	ConfiguracionService cs;

	@GetMapping("/listar")
	public String listarCategorias(Model model) {
		model.addAttribute("lista", cs.getListaConfiguraciones());
		return "configuracion/lista_configuracion";
	}
	
	@GetMapping("/editar/{id}")
	public String editarConfiguracionForm(@PathVariable int id, Model model) {
		Configuracion c = cs.getConfiguracionxId(id);
		model.addAttribute("categoria", c);
		return "configuracion/edit_configuracion";
	}

	@PostMapping("/editar/submit")
	public String editarCategoria(@ModelAttribute Configuracion configuracion) {
		cs.edit(configuracion);
		return "redirect:/configuracion/listar";
	}
}
