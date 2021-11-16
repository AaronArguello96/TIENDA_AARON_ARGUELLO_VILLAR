package curso.spring.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import curso.spring.modelo.Categoria;
import curso.spring.service.CategoriaService;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	CategoriaService cs;

	@GetMapping("/listar")
	public String listarCategorias(Model model) {
		model.addAttribute("lista", cs.getListaCategorias());
		return "categoria/lista_categorias";
	}

	@GetMapping("/crear")
	public String formCrearCategoria(Model model) {
		model.addAttribute("categoria", new Categoria());
		return "categoria/nueva_categoria";
	}

	@PostMapping("/agregar")
	public String agregarCategoria(Model model, Categoria categoria) {
		cs.addCategoria(categoria);
		return "redirect:/categoria/listar";
	}

	@PostMapping("/crear/submit")
	public String crear(@ModelAttribute Categoria categoria) {
		cs.addCategoria(categoria);
		return "redirect:/categoria/lista_categorias";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable int id) {
		cs.delCategoria(id);
		return "redirect:/categoria/listar";
	}

	@GetMapping("/editar/{id}")
	public String editarCategoriaForm(@PathVariable int id, Model model) {
		Categoria c = cs.getCategoriaxId(id);
		model.addAttribute("categoria", c);
		return "categoria/edit_categoria";
	}

	@PostMapping("/editar/submit")
	public String editarCategoria(@ModelAttribute Categoria categoria) {
		cs.editCategoria(categoria);
		return "redirect:/categoria/listar";
	}
}
