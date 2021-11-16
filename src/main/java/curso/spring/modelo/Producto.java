package curso.spring.modelo;

import java.text.ParseException;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Producto {
	
	@Id @GeneratedValue
	private int id;
	private int id_categoria;
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;
	private Date fechaAlta; //private Date fecha_alta;
	private Date fechaBaja; //private Date fecha_baja;
	private float impuesto;
	private String imagen;
	
	public Producto(int id, int id_categoria, String nombre, String descripcion, double precio, int stock,
			Date fechaAlta, float impuesto, String imagen) throws ParseException {
		super();
		this.id = id;
		this.id_categoria = id_categoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.fechaAlta = new Date();
		this.fechaBaja = fechaBaja;
		this.impuesto = impuesto;
		this.imagen = imagen;
	}
	
	public Producto(int id, int id_categoria, String nombre, String descripcion, double precio, int stock,
			float impuesto, String imagen) {
		super();
		this.id = id;
		this.id_categoria = id_categoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.fechaAlta = new Date();
		this.fechaBaja = fechaBaja;
		this.impuesto = impuesto;
		this.imagen = imagen;
	}
	
	public Producto() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Date getfechaAlta() {
		return fechaAlta;
	}

	public void setfechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getfechaBaja() {
		return fechaBaja;
	}

	public void setfechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public float getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(float impuesto) {
		this.impuesto = impuesto;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", id_categoria=" + id_categoria + ", nombre=" + nombre + ", descripcion="
				+ descripcion + ", precio=" + precio + ", stock=" + stock + ", fechaAlta=" + fechaAlta
				+ ", fechaBaja=" + fechaBaja + ", impuesto=" + impuesto + ", imagen=" + imagen + "]";
	}
	


}
