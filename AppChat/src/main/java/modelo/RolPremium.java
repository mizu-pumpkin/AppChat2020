package modelo;

public class RolPremium implements Rol {

	private Descuento descuento;
	
	public RolPremium() {
		this.descuento = null;
	}
	
	public RolPremium(Descuento descuento) {
		this.descuento = descuento;
	}
	
	public Descuento getDescuento() {
		return descuento;
	}
	
	public void realizarPago() {
		//TODO
	}
}
