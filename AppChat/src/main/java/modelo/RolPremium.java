package modelo;

public class Premium implements RolUsuario {

	private Descuento descuento;
	
	public Premium(Descuento descuento) {
		this.descuento = descuento;
	}
	
	public Descuento getDescuento() {
		return descuento;
	}
	
	public void realizarPago() {
		//TODO
	}
}
