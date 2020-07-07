package modelo;

public interface Descuento {
	
	public double getValorDescuento();

	public default double calcDescuento(double price) {
		return (double) Math.round(price * getValorDescuento() * 100) / 100;
	}
	
	public default double precioDescontado(double price) {
		return price - calcDescuento(price);
	}
	
}
