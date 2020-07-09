package modelo;

public class DescuentoVerano implements Descuento {

	public static final double valorDescuento = 0.1;

	@Override
	public double getValorDescuento() {
		return valorDescuento;
	}

}
