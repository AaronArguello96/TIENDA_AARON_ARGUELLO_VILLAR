package curso.spring.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MetodoPago {
	
	@Id @GeneratedValue
	private int id;
	private String paymentMethod;
	
	public MetodoPago() {
		
	}

	public MetodoPago(int id, String paymentMethod) {
		super();
		this.id = id;
		this.paymentMethod = paymentMethod;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	


	
	
	
}
