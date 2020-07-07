package modelo;

public class DescuentoJoven implements Descuento {

	public static final double descuento = 0.3;

	@Override
	public double getValorDescuento() {
		return descuento;
	}

}
