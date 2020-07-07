package modelo;

public class DescuentoVerano implements Descuento {

	public static final double descuento = 0.1;

	@Override
	public double getValorDescuento() {
		return descuento;
	}

}
