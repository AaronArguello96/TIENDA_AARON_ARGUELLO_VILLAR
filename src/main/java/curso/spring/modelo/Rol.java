package curso.spring.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Rol {
	
	@Id @GeneratedValue
	private int id;
	private String rol;
	
	public Rol(int id, String rol) {
		super();
		this.id = id;
		this.rol = rol;
	}
	
	public Rol() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}

}
