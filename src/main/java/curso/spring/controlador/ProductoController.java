package curso.spring.controlador;

import java.io.File;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import curso.spring.modelo.Producto;
import curso.spring.service.ProductoService;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
@RequestMapping("/producto")
public class ProductoController {

	@Autowired
	ProductoService ps; // = new ProductoDAO();

	@GetMapping("/listar")
	public String listarProductos(Model model) {
		
		model.addAttribute("lista", ps.getListaProductos());
		return "producto/lista_productos";
	}
	
	@GetMapping("/filtroPrecio")
	public String listarxPrecio(Model model) {
		List<Producto> listaProductos = ps.getListaProductos();
		listaProductos.sort(Comparator.comparing(Producto::getPrecio));
		model.addAttribute("lista", listaProductos);
		return "index";
	}
	
	@GetMapping("/filtroCategoria")
	public String listarxCategoria(Model model) {
		List<Producto> listaProductos = ps.getListaProductos();
		listaProductos.sort(Comparator.comparing(Producto::getId_categoria));
		model.addAttribute("lista", listaProductos);
		return "index";
	}
	
	@GetMapping("/crear")
	public String formCrearProducto(Model model) {
		model.addAttribute("producto", new Producto());
		return "producto/nuevo_producto";
	}

	@PostMapping("/agregar")
	public String agregarProducto(Model model, Producto producto) {
		String nombreImagen = producto.getImagen();
		String rutaImagen = "/img/";
		producto.setImagen(rutaImagen+nombreImagen);
		ps.addProducto(producto);
		return "redirect:/producto/listar";
	}

	@PostMapping("/crear/submit")
	public String crear(@ModelAttribute Producto producto) {
		ps.addProducto(producto);
		return "redirect:/producto/listarProductos";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable int id) {
		ps.delProducto(id);
		return "redirect:/producto/listar";
	}

	@GetMapping("/editar/{id}")
	public String editarProductoForm(@PathVariable int id, Model model) {
		Producto p = ps.getProductoxId(id);
		model.addAttribute("producto", p);
		return "producto/edit_producto";
	}

	@PostMapping("/editar/submit")
	public String editarProducto(@ModelAttribute Producto producto) {
		String nombreImagen = producto.getImagen();
		String rutaImagen = "/img/";
		producto.setImagen(rutaImagen+nombreImagen);
		ps.addProducto(producto);
		ps.editProducto(producto);
		return "redirect:/producto/listar";
	}
	
	
	@GetMapping("/detalles/{id}")
	public String detallesProducto(@PathVariable int id, Model model) {
		Producto p = ps.getProductoxId(id);
		model.addAttribute("producto", p);
		return "producto/detalles_producto";
	}
	
	@GetMapping("/baja/{id}")
	public String bajaProducto(@PathVariable int id, @ModelAttribute Producto producto) {
		Producto p = ps.getProductoxId(id);
		p.setfechaBaja(new Date());
		ps.editProducto(p);
		return "redirect:/producto/listar";
	}
	
	@GetMapping("/exportar")
	public String exportarProductos() {
		File fichero = new File("./excel/listaProductos.xls");
        try {
        	WritableWorkbook w = Workbook.createWorkbook(fichero);
        	List<Producto> listaproductos = ps.getListaProductos();
        	int contador = 1;
        	
        	//Nombre de la hoja
        	WritableSheet sheet = w.createSheet("Datos", 0);
        	
        	jxl.write.Label cabecera1 = new jxl.write.Label(0, 0, "ID");
        	jxl.write.Label cabecera2 = new jxl.write.Label(1, 0, "IDCATEGORIA");
        	jxl.write.Label cabecera3 = new jxl.write.Label(2, 0, "NOMBRE");
        	jxl.write.Label cabecera4 = new jxl.write.Label(3, 0, "DESCRIPCIÓN");
        	jxl.write.Label cabecera5 = new jxl.write.Label(4, 0, "PRECIO");
        	jxl.write.Label cabecera6 = new jxl.write.Label(5, 0, "STOCK");
        	jxl.write.Label cabecera7 = new jxl.write.Label(6, 0, "IMPUESTO");
        	jxl.write.Label cabecera8 = new jxl.write.Label(7, 0, "IMAGEN");
        	sheet.addCell(cabecera1);
            sheet.addCell(cabecera2);
            sheet.addCell(cabecera3);
            sheet.addCell(cabecera4);
            sheet.addCell(cabecera5);
            sheet.addCell(cabecera6);
            sheet.addCell(cabecera7);
            sheet.addCell(cabecera8);
        	
        	for(Producto p: listaproductos) {
        		
        		jxl.write.Number id = new jxl.write.Number(0, contador, p.getId());
        		jxl.write.Number idCategoria = new jxl.write.Number(1,contador,p.getId_categoria());
            	jxl.write.Label nombre = new jxl.write.Label(2, contador, p.getNombre());
            	jxl.write.Label descripcion = new jxl.write.Label(3, contador, p.getDescripcion());
            	jxl.write.Number precio = new jxl.write.Number(4,contador,p.getPrecio());
            	jxl.write.Number stock = new jxl.write.Number(5,contador,p.getStock());
            	jxl.write.Number impuesto = new jxl.write.Number(6,contador,p.getImpuesto());
            	jxl.write.Label imagen = new jxl.write.Label(7, contador, p.getImagen());
            	sheet.addCell(id);
                sheet.addCell(idCategoria);
                sheet.addCell(nombre);
                sheet.addCell(descripcion);
                sheet.addCell(precio);
                sheet.addCell(stock);
                sheet.addCell(impuesto);
                sheet.addCell(imagen);
                contador++;
        	}

            w.write();
            w.close();
        	
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return "redirect:/producto/listar";
	}
	/*
	@GetMapping("/importar")
	public String importarProductos() {
		File fichero = new File("./excel/listaImportarProductos.xls");
        try {
        	Workbook w = Workbook.getWorkbook(fichero);
        	
        	//Se lee la primera hoja de la excel
        	Sheet sheet = w.getSheet(0);

        	for (int f=0; f<sheet.getRows(); f++) {
        		String contenido = " ";
        		for(int c=0;c<sheet.getColumns();c++) {
        			contenido += sheet.getCell(c,f).getContents() + "\t";// + sheet.getCell(c, f).getContents();
        		}
        		System.out.println(contenido);	
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return "redirect:/producto/listar";
	}*/
}