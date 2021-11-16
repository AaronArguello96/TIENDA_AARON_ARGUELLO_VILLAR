package curso.spring.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Pedido {
	
	//Estados de los pedidos
	public static String ENVIADO = "Enviado";
	public static String PENDIENTE = "Pendiente";
	public static String PENDIENTE_CANCELAR = "Pendiente de cancelar";
	public static String CANCELADO = "Cancelado";
	
	@Id @GeneratedValue
	private int id;
	private int idUsuario;
	private Date fecha;
	private String metodoPago;
	private String estado;
	private String num_factura;
	private double total;
	
	public Pedido() {
		
	}
	
	public Pedido(int id, int idUsuario, Date fecha, String metodoPago, String estado, String num_factura,
			double total) {
		super();
		this.id = id;
		this.idUsuario = idUsuario;
		this.fecha = fecha;
		this.metodoPago = metodoPago;
		this.estado = estado;
		this.num_factura = num_factura;
		this.total = total;
	}
	
	public Pedido(int idUsuario, String metodoPago, String estado, String num_factura,
			double total) {
		super();
		this.idUsuario = idUsuario;
		this.fecha = new Date();
		this.metodoPago = metodoPago;
		this.estado = estado;
		this.num_factura = num_factura;
		this.total = total;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getidUsuario() {
		return idUsuario;
	}
	public void idUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getMetodoPago() {
		return metodoPago;
	}
	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNum_factura() {
		return num_factura;
	}
	public void setNum_factura(String num_factura) {
		this.num_factura = num_factura;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
	
}
