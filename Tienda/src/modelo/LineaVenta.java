package modelo;

public class LineaVenta {

	private int codigo;
	private int unidades;
	private Producto producto;
	
	public LineaVenta(int unidades, Producto producto){
		codigo = 0;
		this.unidades = unidades;
		this.producto = producto;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getUnidades() {
		return unidades;
	}
	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public double getSubTotal() {
		return unidades * producto.getPrecio();
	}
}
