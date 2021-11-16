package curso.spring.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DetallesPedido {
	@Id @GeneratedValue
	private int id;
	private int idPedido;
	private int idProducto;
	private String nombre_pedido;
	private double precio_unidad;
	private int unidades;
	private float impuesto;
	private double total;
	
	public DetallesPedido(int idProducto, String nombre_pedido, double precio_unidad,
			int unidades, float impuesto, double total) {
		this.idProducto = idProducto;
		this.nombre_pedido = nombre_pedido;
		this.precio_unidad = precio_unidad;
		this.unidades = unidades;
		this.impuesto = impuesto;
		this.total = total;
	}
	
	
	public DetallesPedido(int id, int idPedido, int idProducto, String nombre_pedido, double precio_unidad,
			int unidades, float impuesto, double total) {
		super();
		this.id = id;
		this.idPedido = idPedido;
		this.idProducto = idProducto;
		this.nombre_pedido = nombre_pedido;
		this.precio_unidad = precio_unidad;
		this.unidades = unidades;
		this.impuesto = impuesto;
		this.total = total;
	}
	
	public DetallesPedido(int id, int idPedido, int idProducto, String nombre_pedido, double precio_unidad,
			int unidades, float impuesto) {
		super();
		this.id = id;
		this.idPedido = idPedido;
		this.idProducto = idProducto;
		this.nombre_pedido = nombre_pedido;
		this.precio_unidad = precio_unidad;
		this.unidades = unidades;
		this.impuesto = impuesto;
		this.total = precio_unidad*unidades;
	}

	public DetallesPedido() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombre_pedido() {
		return nombre_pedido;
	}

	public void setNombre_pedido(String nombre_pedido) {
		this.nombre_pedido = nombre_pedido;
	}

	public double getPrecio_unidad() {
		return precio_unidad;
	}

	public void setPrecio_unidad(double precio_unidad) {
		this.precio_unidad = precio_unidad;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public float getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(float impuesto) {
		this.impuesto = impuesto;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}	
	
	
}
