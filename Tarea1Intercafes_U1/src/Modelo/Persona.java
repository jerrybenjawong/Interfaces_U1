package Modelo;
//Clase que representa un contacto en nuestra agenda
public class Persona {
	//Atributos basicos del contacto 
	private String nombre;
	private String telefono;
	private String correo;
	private String categoria;
	//constructor que sirve para crear un objeto persona con los datos que pasemos
	public Persona(String nombre, String telefono, String correo, String categoria) {
		this.nombre=nombre;
		this.telefono=telefono;
		this.correo=correo;
		this.categoria=categoria;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	//toString sirve para convertir el objeto persona en una linea de texto 
	//sirve mas para guardar en archivos 
	public String toString() {
		return nombre + "," +telefono+ "," +correo+ "," +categoria;
	}
}
