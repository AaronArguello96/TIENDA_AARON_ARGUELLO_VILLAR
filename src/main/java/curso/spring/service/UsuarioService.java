package curso.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.spring.modelo.Usuario;
import curso.spring.repositorio.*;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repo;
	
	//private ArrayList<Usuario> listaUsuarios = cargarUsuarios();
	
	@PostConstruct
	public void cargarUsuarios(){
		//ArrayList<Usuario> lista = new ArrayList<Usuario>();
		Base64 base64 = new Base64();
		String password = "1234";
		String passwordEmpleado = "123456789";
		String passEncriptada = new String(base64.encode(password.getBytes()));
		String passEncriptadaEmpleado = new String(base64.encode(passwordEmpleado.getBytes()));
		Usuario u = new Usuario(1, 1, "Admin", passEncriptada, "admin@admin", "Admin", "Admin", "C/Administrador", "Zamora", "Zamora", "78549625", "12345678X");
		repo.save(u);
		u = new Usuario(2, 2, "Aarón", passEncriptadaEmpleado, "aaron@empleado", "Argüello", "Villar", "C/La Iglesia", "Zamora", "Melgar de Tera", "78541227", "98754623T");
		repo.save(u);
		u = new Usuario(3, 3, "Pepe", passEncriptada, "pepe@cliente", "Fernández", "Pérez", "C/Santa Clara", "Benavente", "Benavente", "45789625", "14785236L" );
		repo.save(u);

	}
	
	public List<Usuario> getListaUsuarios(){ 
		return repo.findAll();
	}

	public void addUsuario(Usuario usuario){
		repo.save(usuario);
	}
	
	public void delUsuario(int id) {
		Usuario u = repo.getById(id);
		repo.delete(u);
	}
	
	public Usuario getUsuarioxId(int id) {
		Usuario u = repo.getById(id);
		return u;
	}
	
	public Usuario getUsuarioxNombre(String nombre) {
		Usuario u = repo.findByNombre(nombre);
		return u;
	}
	
	public Usuario getUsuarioxEmail(String email) {
		Usuario u = repo.findByEmail(email);
		return u;
	}
	
	public void editUsuario(Usuario usuario){
		repo.save(usuario);
	}
	
	public boolean comprobarLogin(Usuario usuario) {
		boolean result = false;
		Base64 base64 = new Base64();
		String passEncriptada = new String(base64.encode(usuario.getClave().getBytes()));
		List<Usuario> lista = repo.buscarUsuarioLogin(usuario.getEmail(), passEncriptada);
		
		if(!lista.isEmpty()) {
			return true;
		}
		return result;
	}
	
	public boolean comprobarEmail(Usuario usuario) {
		boolean result = false;
		Usuario u = repo.findByEmail(usuario.getEmail());
		if(u==null) {
			return true;
		}

		return result;
	}
	
	public String encriptarPassword(String clave) {
		Base64 base64 = new Base64();
		String passEncriptada = new String(base64.encode(clave.getBytes()));
		return passEncriptada;
	}

	public ArrayList<Usuario> listaUsuarioxRol(int id){
		return (ArrayList<Usuario>) repo.findByIdrol(id);
	}
}
