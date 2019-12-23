package modelo;

import java.util.LinkedList;
import java.util.List;

public class Cliente {
	private int codigo;
	private String dni;
	private String nombre;
	private List<Venta> ventas;

	public Cliente(String dni, String nombre) {
		codigo = 0;
		this.dni = dni;
		this.nombre = nombre;
		ventas = new LinkedList<Venta>();
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDni() {
		return dni;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void addVenta(Venta v) {
		ventas.add(v);
	}

	public List<Venta> getVentas() {
		return ventas;
	}

}
