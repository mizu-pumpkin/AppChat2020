package modelo;

public class Producto {

	private int codigo;
	private double precio;
	private String nombre;
	private String descripcion;
	
	public Producto(double precio, String nombre, String descripcion) {
		this.codigo = 0;
		this.precio = precio;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
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

	//Util para mostrar el objeto en ComboBox
	@Override
	public String toString() {
		return nombre;
	}
	

}
