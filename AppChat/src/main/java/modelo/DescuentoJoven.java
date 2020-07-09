package modelo;

public class DescuentoJoven implements Descuento {

	public static final double valorDescuento = 0.3;

	@Override
	public double getValorDescuento() {
		return valorDescuento;
	}

}
