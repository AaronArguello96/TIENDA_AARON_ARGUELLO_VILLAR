package curso.spring.controlador;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import curso.spring.modelo.Configuracion;
import curso.spring.modelo.DetallesPedido;
import curso.spring.modelo.MetodoPago;
import curso.spring.modelo.Pedido;
import curso.spring.modelo.Usuario;
import curso.spring.service.ConfiguracionService;
import curso.spring.service.DetallesPedidoService;
import curso.spring.service.MetodoPagoService;
import curso.spring.service.PedidoService;
import curso.spring.service.UsuarioService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired
	DetallesPedidoService dps;
	@Autowired
	PedidoService ps;
	@Autowired
	UsuarioService us;
	@Autowired
	MetodoPagoService mps;
	@Autowired
	ConfiguracionService cs;
	
	
	@GetMapping("/registrarPedido")
	public String registrarPedidoCarrito(HttpSession sesion, @ModelAttribute MetodoPago metodoPago) {
		
		//Añadir el id del pedido a la lista de detalles pedido y y borrarlo del carrito cada vez que se añada al carrito
		ArrayList<DetallesPedido> carrito = (ArrayList<DetallesPedido>)sesion.getAttribute("carrito");
		
		Usuario u = (Usuario) sesion.getAttribute("usuario");
		
		Double total = (Double) sesion.getAttribute("total");
		
		Pedido p = new Pedido(u.getId(), metodoPago.getPaymentMethod(), Pedido.PENDIENTE, "",  total); 
		
		ps.addPedido(p);
		
		//Añadimos todos los productos del pedido a la tabla detalles pedido
		for(int i = 0; i<carrito.size(); i++) {
			DetallesPedido dp = carrito.get(i);
			dp.setIdPedido(p.getId());
			dps.addDetalles_pedido(dp);
		}
		
		carrito.clear();
		sesion.setAttribute("total", 0);
		
		return "redirect:/";
	}
	/*
	@GetMapping("/listaPedidos")
	public String mostrarPedidos(Model model) {
		model.addAttribute("lista", ps.getListaPedidos());
		return "pedido/listaPedidos";
	}*/
	
	@GetMapping("/listaPedidos")
	public String mostrarPedidos(Model model, HttpSession sesion) {
		Usuario u = (Usuario) sesion.getAttribute("usuario");
		model.addAttribute("lista", ps.getListaPedidosxIdUsuario(u.getId()));
		return "pedido/listaPedidos";
	}
	
	@GetMapping("/detallesPedido/{id}")
	public String detallesPedido(@PathVariable int id, Model model) {
		model.addAttribute("lista", dps.getDetalles_pedidoxIdPedido(id));
		return "pedido/detallesPedido";
	}
	
	//+-----------------------------GESTION ADMIN Y EMPLEADO----------------------------+
	
	@GetMapping("/listaPedidosTotal")
	public String mostrarPedidos(Model model) {
		model.addAttribute("lista", ps.getListaPedidos());
		return "pedido/listaPedidosTotal";
	}
	//+---------------------------------------------------------------------------------+
	
	@GetMapping("/cambiarEstadoEnviado/{id}")
	public String cambiarEstadoEnviado(@PathVariable int id, Model model) {
		Pedido p = ps.getPedidoxId(id);
		if(p.getEstado().equals(p.PENDIENTE)) {
			Configuracion c = cs.getListaByClave(Configuracion.NUM_FACTURA);
			String num = c.getValor();
			num = numeroFactura(num);
			c.setValor(num);
			p.setNum_factura(num);
			p.setEstado(p.ENVIADO);
			ps.editPedido(p);		
			cs.edit(c);
		return "redirect:/pedido/listaPedidosTotal/";
		}else {
			model.addAttribute("mensaje", "Ya se ha actualizado el estado del pedido");
			return "redirect:/pedido/listaPedidosTotal/";
		}
	}
	
	@GetMapping("/cambiarEstadoSCancelado/{id}")
	public String cambiarEstadoSCancelado(@PathVariable int id, Model model) {
		Pedido p = ps.getPedidoxId(id);	
		p.setEstado(p.PENDIENTE_CANCELAR);
		ps.editPedido(p);
		return "redirect:/pedido/listaPedidos/";
	}
	
	@GetMapping("/cambiarEstadoCancelado/{id}")
	public String cambiarEstadoCancelado(@PathVariable int id, Model model) {
		Pedido p = ps.getPedidoxId(id);
		if(p.getEstado().equals(p.PENDIENTE_CANCELAR)) {
			p.setEstado(p.CANCELADO);
			ps.editPedido(p);
			return "redirect:/pedido/listaPedidosTotal/";
		}else {
			model.addAttribute("mensaje", "Ya se ha actualizado el estado del pedido");
			return "redirect:/pedido/listaPedidosTotal/";
		}

	}
	
	//Método que calcula el numero de factura, empezando por 0 y sumando uno a cada pedido nuevo que se haga
	public String numeroFactura(String num) {
		
		String factura = num.substring(0,5);
		int n = Integer.parseInt(num.substring(5));
		n = n+1;
		Formatter fmt = new Formatter();
		factura += fmt.format("%04d",n);
		fmt.close();
		
		return factura;
	}
	
	@GetMapping("/descargarFactura/{id}")
	public String obtenerFactura(@PathVariable int id, Model model) throws FileNotFoundException, DocumentException {
		Pedido p = ps.getPedidoxId(id);
		if(p.getEstado().equals(p.ENVIADO)) {
			Document documento = new Document(PageSize.A4, 20, 20, 70, 50);
			PdfWriter writer = null;
			
			try {
				Phrase linea;
				Phrase imgCabecera;
				//Phrase txtCabecera;
				Image imagen;
				Phrase txtFecha;
				Phrase txtnumFactura;
				String total = String.valueOf(p.getTotal());
				//List<Configuracion> listaConf = cs.getListaConfiguraciones();
				
				writer = PdfWriter.getInstance(documento, new FileOutputStream("./facturas/factura.pdf"));
				documento.open();	
				
				//CABECERA
				PdfContentByte cb = writer.getDirectContent();
				imagen = Image.getInstance("C:/Users/aaron/Documents/workspace-spring-tool-suite-4-4.12.1.RELEASE/TIENDA_AARON_ARGUELLO_VILLAR/src/main/resources/static/img/logo.png");
				Chunk chunk = new Chunk(imagen, 0, -60);
				imgCabecera = new Phrase(chunk);
				ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, imgCabecera, documento.rightMargin() + 90,
						documento.top() + 60, 0);
				
				SimpleDateFormat formateador = new SimpleDateFormat("dd/mm/yyyy");
				String fecha = formateador.format(new Date());
				txtFecha = new Phrase(fecha, new Font(FontFactory.getFont("Sans", 8, Font.NORMAL, BaseColor.BLACK)));
				
				String numFactura = p.getNum_factura();
				txtnumFactura = new Phrase(numFactura, new Font(FontFactory.getFont("Sans", 8, Font.NORMAL, BaseColor.BLACK)));
				ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, txtFecha, (documento.right() - documento.left()),
						documento.top() + 30, 0);
				 
				//linea de arriba
				linea = new Phrase();
				linea.add(new LineSeparator(1, new Float(2.8), BaseColor.BLACK, Element.ALIGN_LEFT, 0));
				ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, linea, documento.rightMargin(), documento.top() - 5, 0);

				//linea de abajo
				linea = new Phrase();
				linea.add(new LineSeparator(1, new Float(2.8), BaseColor.BLACK, Element.ALIGN_LEFT, 0));
				ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, linea, documento.rightMargin(), documento.top() - 730, 0);
				
				//CONTENIDO	
				
				Paragraph paragraph = new Paragraph();
				Paragraph paragraph2 = new Paragraph();
				Paragraph paragraph3 = new Paragraph();
				Paragraph espacio = new Paragraph();
				
				espacio.add(" ");
				documento.add(espacio);
				paragraph.add("FACTURA DEL PEDIDO");
				documento.add(paragraph);
				espacio.add(" ");
				documento.add(espacio);
				
				//TABLA DE PEDIDO
				PdfPTable tabla = new PdfPTable(3);
			    Phrase texto = new Phrase("cabecera");
				PdfPCell cabecera = new PdfPCell(texto);
				cabecera.setBackgroundColor(BaseColor.DARK_GRAY);
				cabecera.setBorderWidth(1);
			    tabla.addCell("Número de factura");
			    tabla.addCell("Método de pago");
			    tabla.addCell("Total");
			    tabla.addCell(p.getNum_factura());
			    tabla.addCell(p.getMetodoPago());
			    tabla.addCell(total + "€");
			    documento.add(tabla);
			    espacio.add(" ");
			    documento.add(espacio);
			    paragraph2.add("DETALLES DEL PEDIDO");
			    documento.add(paragraph2);
			    documento.add(espacio);
			    
			    //TABLA DE DETALLES PEDIDO
				PdfPTable tabladetalles = new PdfPTable(5);
			    Phrase textocabecera = new Phrase("cabecera");
				PdfPCell cabeceradetalles = new PdfPCell(textocabecera);
				cabeceradetalles.setBackgroundColor(BaseColor.BLUE);
				cabeceradetalles.setBorderWidth(1);
				tabladetalles.addCell("Nombre del Producto");
				tabladetalles.addCell("Precio (unidad)");
				tabladetalles.addCell("Unidades");
			    tabladetalles.addCell("Impuesto");
			    tabladetalles.addCell("Total");
				List<DetallesPedido> listadetalles = dps.getDetalles_pedidoxIdPedido(p.getId());
				for(DetallesPedido dp: listadetalles) {
				    tabladetalles.addCell(dp.getNombre_pedido());
				    tabladetalles.addCell(String.valueOf(dp.getPrecio_unidad() + "€"));
				    tabladetalles.addCell(String.valueOf(dp.getUnidades()));
				    tabladetalles.addCell(String.valueOf(dp.getImpuesto() + "%"));
				    tabladetalles.addCell(String.valueOf(dp.getTotal() + "€"));		
				}
				documento.add(tabladetalles);

				//PIE
				Phrase pie = new Phrase(String.format("Página %d", writer.getCurrentPageNumber()));
				ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, pie,(documento.right() - documento.left()) / 2 + documento.leftMargin(), documento.bottom() - 20, 0);

				documento.close();
				writer.close(); //Cerramos writer
			}catch(Exception e) {
				e.printStackTrace();
				
			}finally {
				if(documento != null) {
					//documento.close();
				}
			}		
		}		
		return "redirect:/pedido/listaPedidos/";
	}
}
